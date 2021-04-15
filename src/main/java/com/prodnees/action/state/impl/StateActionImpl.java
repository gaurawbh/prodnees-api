package com.prodnees.action.state.impl;

import com.prodnees.action.BatchStateList;
import com.prodnees.action.state.StateAction;
import com.prodnees.controller.DocumentController;
import com.prodnees.domain.Document;
import com.prodnees.domain.batch.RawProduct;
import com.prodnees.domain.enums.StateStatus;
import com.prodnees.domain.state.Event;
import com.prodnees.domain.state.State;
import com.prodnees.domain.state.StateApprovalDocument;
import com.prodnees.model.RawProductModel;
import com.prodnees.model.state.EventModel;
import com.prodnees.model.state.StateApprovalDocumentModel;
import com.prodnees.model.state.StateModel;
import com.prodnees.service.DocumentService;
import com.prodnees.service.batch.RawProductService;
import com.prodnees.service.rels.BatchRightService;
import com.prodnees.service.rels.StateApprovalDocumentService;
import com.prodnees.service.state.EventService;
import com.prodnees.service.state.StateService;
import com.prodnees.util.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StateActionImpl implements StateAction {

    private final StateService stateService;
    private final StateApprovalDocumentService stateApprovalDocumentService;
    private final EventService eventService;
    private final RawProductService rawProductService;
    private final DocumentService documentService;
    private final BatchRightService batchRightService;
    private final BatchStateList batchStateList;


    public StateActionImpl(StateService stateService,
                           StateApprovalDocumentService stateApprovalDocumentService,
                           EventService eventService,
                           RawProductService rawProductService,
                           DocumentService documentService,
                           BatchRightService batchRightService,
                           BatchStateList batchStateList) {
        this.stateService = stateService;
        this.stateApprovalDocumentService = stateApprovalDocumentService;
        this.eventService = eventService;
        this.rawProductService = rawProductService;
        this.documentService = documentService;
        this.batchRightService = batchRightService;
        this.batchStateList = batchStateList;
    }

    @Override
    public boolean existsByBatchId(int batchProductId) {
        return stateService.existsByBatchId(batchProductId);
    }

    @Override
    public boolean hasStateEditorRights(int id, int editorId) {
        Optional<State> stateOptional = findById(id);
        if (stateOptional.isEmpty()) {
            return false;
        } else {
            return batchRightService.hasBatchEditorRights(stateOptional.get().getBatchId(), editorId);
        }

    }

    @Override
    public boolean hasStateReaderRights(int id, int readerId) {
        Optional<State> stateOptional = findById(id);
        if (stateOptional.isEmpty()) {
            return false;
        } else {
            return batchRightService.hasBatchReaderRights(stateOptional.get().getBatchId(), readerId);
        }
    }

    @Override
    public boolean existsById(int id) {
        return stateService.existsById(id);
    }

    /**
     * State must be validated before calling save method
     * <p>Last State Validated</p>
     * <p>Next State Validated</p>
     *
     * @param state
     * @return
     */
    @Override
    public StateModel save(State state) {
        if (state.getName().isBlank()) {
            state.setName(String.format("State-%d-%d", state.getBatchId(), batchStateList.size(state.getBatchId())));
        }
        return entityToModel(batchStateList.add(state));
    }

    @Override
    public Optional<State> findById(int id) {
        return stateService.findById(id);
    }

    @Override
    public State getById(int id) {
        return stateService.getById(id);
    }

    @Override
    public StateModel getModelById(int id) {
        return entityToModel(stateService.getById(id));
    }

    @Override
    public State getByName(String name) {
        return stateService.getByName(name);
    }

    @Override
    public List<StateModel> getAllByBatchId(int batchProductId) {
        List<State> stateList = stateService.getAllByBatchId(batchProductId);
        List<StateModel> stateModelList = new ArrayList<>();
        stateList.forEach(state -> stateModelList.add(entityToModel(state)));
        return stateModelList;
    }

    @Override
    public List<State> getAllByBatchIdAndStatus(int batchProductId, StateStatus status) {
        return stateService.getAllByBatchIdAndStatus(batchProductId, status);
    }

    @Override
    public void deleteById(int id) {
        State state = stateService.getById(id);
        batchStateList.remove(state);
    }

    private StateModel entityToModel(State state) {
        StateModel stateModel = new StateModel();
        List<StateApprovalDocument> stateApprovalDocumentList = stateApprovalDocumentService.getAllByStateId(state.getId());
        List<Event> eventList = eventService.getAllByStateId(state.getId());
        List<RawProduct> rawProductList = rawProductService.getAllByStateId(state.getId());
        List<StateApprovalDocumentModel> stateApprovalDocumentModelList = new ArrayList<>();
        stateApprovalDocumentList.forEach(stateApprovalDocument -> stateApprovalDocumentModelList.add(entityToModel(stateApprovalDocument)));
        List<EventModel> eventModelList = new ArrayList<>();
        eventList.forEach(event -> eventModelList.add(entityToModel(event)));
        List<RawProductModel> rawProductModelList = new ArrayList<>();
        rawProductList.forEach(rawProduct -> rawProductModelList.add(entityToModel(rawProduct)));
        stateModel.setId(state.getId())
                .setBatchId(state.getBatchId())
                .setIndex(state.getIndex())
                .setName(state.getName())
                .setDescription(state.getDescription())
                .setApprovalDocuments(stateApprovalDocumentModelList)
                .setEventModelList(eventModelList)
                .setRawProductModelList(rawProductModelList)
                .setStatus(state.getStatus());
        return stateModel;
    }

    private StateApprovalDocumentModel entityToModel(StateApprovalDocument stateApprovalDocument) {
        Document document = documentService.getById(stateApprovalDocument.getDocumentId());

        return new StateApprovalDocumentModel()
                .setId(stateApprovalDocument.getId())
                .setName(document.getName())
                .setDocumentId(stateApprovalDocument.getDocumentId())
                .setApproverId(stateApprovalDocument.getApproverId())
                .setApproverEmail(stateApprovalDocument.getApproverEmail())
                .setDocumentUrl(MvcUriComponentsBuilder.fromController(DocumentController.class)
                        .path("document/load")
                        .queryParam("id", document.getId())
                        .toUriString())
                .setDocumentDownloadUrl(MvcUriComponentsBuilder.fromController(DocumentController.class)
                        .path("document/download")
                        .queryParam("id", document.getId())
                        .toUriString())
                .setApprovalDocumentState(stateApprovalDocument.getState());

    }

    private EventModel entityToModel(Event event) {
        return MapperUtil.getDozer().map(event, EventModel.class);
    }

    private RawProductModel entityToModel(RawProduct rawProduct) {
        return MapperUtil.getDozer().map(rawProduct, RawProductModel.class);

    }
}
