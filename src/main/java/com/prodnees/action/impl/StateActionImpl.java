package com.prodnees.action.impl;

import com.prodnees.action.StateAction;
import com.prodnees.controller.DocumentController;
import com.prodnees.domain.Document;
import com.prodnees.domain.Event;
import com.prodnees.domain.RawProduct;
import com.prodnees.domain.State;
import com.prodnees.domain.StateApprovalDocument;
import com.prodnees.dto.StateDto;
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
    public StateModel save(State state) {
        return entityToModel(stateService.save(state));
    }

    @Override
    public State save(StateDto stateDto) {
        State state = MapperUtil.getDozer().map(stateDto, State.class);
        return stateService.save(state);
    }

    @Override
    public State getById(int id) {
        return stateService.getById(id);
    }

    @Override
    public State getByName(String name) {
        return stateService.getByName(name);
    }

    @Override
    public List<State> getAllByBatchProductId(int batchProductId) {
        return stateService.getAllByBatchProductId(batchProductId);
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

    //todo
    private StateModel entityToModel(State state) {
        StateModel stateModel = new StateModel();
        List<StateApprovalDocument> stateApprovalDocumentList = stateApprovalDocumentService.getAllByStateId(state.getId());
        List<Event> eventList = eventService.getAllByStateId(state.getId());
        List<RawProduct> rawProductList = rawProductService.getAllByStateId(state.getId());
        List<StateApprovalDocumentModel> stateApprovalDocumentModelList = new ArrayList<>();
        stateApprovalDocumentList.forEach(stateApprovalDocument -> stateApprovalDocumentModelList.add(entityToModel(stateApprovalDocument)));
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

    // TODO: 31/03/2021
    private EventModel entityToModel(Event event) {
        return new EventModel();

    }
// TODO: 31/03/2021
    private RawProductModel entityToModel(RawProduct rawProduct) {
        return new RawProductModel();

    }
}
