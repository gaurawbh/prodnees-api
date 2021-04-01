package com.prodnees.action.impl;

import com.prodnees.action.StateAction;
import com.prodnees.controller.DocumentController;
import com.prodnees.domain.Document;
import com.prodnees.domain.Event;
import com.prodnees.domain.RawProduct;
import com.prodnees.domain.State;
import com.prodnees.domain.StateApprovalDocument;
import com.prodnees.model.EventModel;
import com.prodnees.model.RawProductModel;
import com.prodnees.model.StateApprovalDocumentModel;
import com.prodnees.model.StateModel;
import com.prodnees.service.DocumentService;
import com.prodnees.service.EventService;
import com.prodnees.service.RawProductService;
import com.prodnees.service.StateService;
import com.prodnees.service.rels.StateApprovalDocumentService;
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

    public StateActionImpl(StateService stateService,
                           StateApprovalDocumentService stateApprovalDocumentService,
                           EventService eventService,
                           RawProductService rawProductService,
                           DocumentService documentService) {
        this.stateService = stateService;
        this.stateApprovalDocumentService = stateApprovalDocumentService;
        this.eventService = eventService;
        this.rawProductService = rawProductService;
        this.documentService = documentService;
    }

    @Override
    public boolean existsByBatchProductId(int batchProductId) {
        return stateService.existsByBatchProductId(batchProductId);
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
        return entityToModel(stateService.save(state));
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
    public List<StateModel> getAllByBatchProductId(int batchProductId) {
        List<State> stateList = stateService.getAllByBatchProductId(batchProductId);
        List<StateModel> stateModelList = new ArrayList<>();
        stateList.forEach(state -> stateModelList.add(entityToModel(state)));
        return stateModelList;
    }

    @Override
    public List<State> getAllByBatchProductIdAndComplete(int batchProductId, boolean isComplete) {
        return stateService.getAllByBatchProductIdAndComplete(batchProductId, isComplete);
    }

    @Override
    public void deleteById(int id) {
        State state = stateService.getById(id);
        Optional<State> tailStateOpt = Optional.ofNullable(stateService.getById(state.getLastStateId()));
        Optional<State> headStateOpt = Optional.ofNullable(stateService.getById(state.getNextStateId()));
        if (tailStateOpt.isPresent() && headStateOpt.isPresent()) {
            tailStateOpt.get().setNextStateId(headStateOpt.get().getId());
            stateService.save(tailStateOpt.get());
            headStateOpt.get().setLastStateId(tailStateOpt.get().getId());
            stateService.save(headStateOpt.get());
        }
        stateService.deleteById(id);
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
                .setBatchProductId(state.getBatchProductId())
                .setName(state.getName())
                .setDescription(state.getDescription())
                .setApprovalDocuments(stateApprovalDocumentModelList)
                .setEventModelList(eventModelList)
                .setRawProductModelList(rawProductModelList)
                .setComplete(state.isComplete())
                .setLastStateId(state.getLastStateId())
                .setNextStateId(state.getNextStateId())
                .setInitialState(state.isInitialState())
                .setFinalState(state.isFinalState());
        return stateModel;
    }

    private StateApprovalDocumentModel entityToModel(StateApprovalDocument stateApprovalDocument) {
        Document document = documentService.getById(stateApprovalDocument.getDocumentId());

        return new StateApprovalDocumentModel()
                .setId(stateApprovalDocument.getId())
                .setName(stateApprovalDocument.getName())
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
