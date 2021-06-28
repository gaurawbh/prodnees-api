package com.prodnees.core.action.stage.impl;

import com.prodnees.core.action.StageList;
import com.prodnees.core.action.stage.StageAction;
import com.prodnees.core.controller.DocumentController;
import com.prodnees.core.domain.NeesDoc;
import com.prodnees.core.domain.batch.RawProduct;
import com.prodnees.core.domain.enums.StageState;
import com.prodnees.core.domain.stage.Stage;
import com.prodnees.core.domain.stage.StageApprovalDocument;
import com.prodnees.core.domain.stage.StageTodo;
import com.prodnees.core.dto.stage.StageDto;
import com.prodnees.core.model.stage.StageApprovalDocumentModel;
import com.prodnees.core.model.stage.StageModel;
import com.prodnees.core.service.NeesDocumentService;
import com.prodnees.core.service.batch.RawProductService;
import com.prodnees.core.service.rels.BatchRightService;
import com.prodnees.core.service.rels.StageApprovalDocumentService;
import com.prodnees.core.service.stage.StageService;
import com.prodnees.core.service.stage.StageTodoService;
import com.prodnees.core.web.exception.NeesNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StageActionImpl implements StageAction {

    private final StageService stageService;
    private final StageApprovalDocumentService stageApprovalDocumentService;
    private final StageTodoService stageTodoService;
    private final RawProductService rawProductService;
    private final NeesDocumentService neesDocumentService;
    private final BatchRightService batchRightService;
    private final StageList stageList;


    public StageActionImpl(StageService stageService,
                           StageApprovalDocumentService stageApprovalDocumentService,
                           StageTodoService stageTodoService,
                           RawProductService rawProductService,
                           NeesDocumentService neesDocumentService,
                           BatchRightService batchRightService,
                           StageList stageList) {
        this.stageService = stageService;
        this.stageApprovalDocumentService = stageApprovalDocumentService;
        this.stageTodoService = stageTodoService;
        this.rawProductService = rawProductService;
        this.neesDocumentService = neesDocumentService;
        this.batchRightService = batchRightService;
        this.stageList = stageList;
    }

    @Override
    public boolean existsByBatchId(int batchId) {
        return stageService.existsByBatchId(batchId);
    }

    @Override
    public boolean hasStageEditorRights(int id, int editorId) {
        Optional<Stage> stateOptional = findById(id);
        if (stateOptional.isEmpty()) {
            return false;
        } else {
            return batchRightService.hasBatchEditorRights(stateOptional.get().getBatchId(), editorId);
        }

    }

    @Override
    public boolean hasStageReaderRights(int id, int readerId) {
        Optional<Stage> stateOptional = findById(id);
        if (stateOptional.isEmpty()) {
            return false;
        } else {
            return batchRightService.hasBatchReaderRights(stateOptional.get().getBatchId(), readerId);
        }
    }

    @Override
    public boolean existsById(int id) {
        return stageService.existsById(id);
    }

    /**
     * if no {@link Stage#getIndx()} is provided in the dto, it goes on top of the stack
     *
     * @param dto
     * @return
     */
    @Override
    public StageModel addNew(StageDto dto) {
        Stage stage = new Stage()
                .setBatchId(dto.getBatchId())
                .setIndx(dto.getIndex())
                .setDescription(dto.getDescription())
                .setState(StageState.OPEN);
        int nextId = stageService.getNextId();
        String batchName = "Stage-" + nextId;
        stage.setName(batchName);
        return entityToModel(stageList.add(stage));
    }

    @Override
    public Optional<Stage> findById(int id) {
        return stageService.findById(id);
    }

    @Override
    public Stage getById(int id) {
        return stageService.findById(id).orElseThrow(() -> new NeesNotFoundException(String.format("Stage with id: %d not found", id)));
    }

    @Override
    public StageModel getModelById(int id) {
        return entityToModel(stageService.getById(id));
    }

    @Override
    public Stage getByName(String name) {
        return stageService.getByName(name);
    }

    @Override
    public List<StageModel> getAllByBatchId(int batchId) {
        List<Stage> stageList = stageService.getAllByBatchId(batchId);
        List<StageModel> stageModelList = new ArrayList<>();
        stageList.forEach(state -> stageModelList.add(entityToModel(state)));
        return stageModelList;
    }

    @Override
    public List<Stage> getAllByBatchIdAndState(int batchId, StageState state) {
        return stageService.getAllByBatchIdAndState(batchId, state);
    }

    @Override
    public void deleteById(int id) {
        Stage stage = stageService.getById(id);
        stageList.remove(stage);
    }

    private StageModel entityToModel(Stage stage) {
        StageModel stageModel = new StageModel();
        List<StageApprovalDocument> stageApprovalDocumentList = stageApprovalDocumentService.getAllByStageId(stage.getId());
        List<StageTodo> stageTodoList = stageTodoService.getAllByStageId(stage.getId());
        List<RawProduct> rawProductList = rawProductService.getAllByStageId(stage.getId());
        List<StageApprovalDocumentModel> stageApprovalDocumentModelList = new ArrayList<>();
        stageApprovalDocumentList.forEach(stateApprovalDocument -> stageApprovalDocumentModelList.add(entityToModel(stateApprovalDocument)));
        stageModel.setId(stage.getId())
                .setBatchId(stage.getBatchId())
                .setIndex(stage.getIndx())
                .setName(stage.getName())
                .setDescription(stage.getDescription())
                .setApprovalDocumentList(stageApprovalDocumentModelList)
                .setStageTodoList(stageTodoList)
                .setRawProductList(rawProductList)
                .setStatus(stage.getState());
        return stageModel;
    }

    private StageApprovalDocumentModel entityToModel(StageApprovalDocument stageApprovalDocument) {
        NeesDoc neesDoc = neesDocumentService.getById(stageApprovalDocument.getDocumentId());

        return new StageApprovalDocumentModel()
                .setId(stageApprovalDocument.getId())
                .setName(neesDoc.getName())
                .setDocumentId(stageApprovalDocument.getDocumentId())
                .setApproverId(stageApprovalDocument.getApproverId())
                .setApproverEmail(stageApprovalDocument.getApproverEmail())
                .setDocumentUrl(MvcUriComponentsBuilder.fromController(DocumentController.class)
                        .path("document/load")
                        .queryParam("id", neesDoc.getId())
                        .toUriString())
                .setDocumentDownloadUrl(MvcUriComponentsBuilder.fromController(DocumentController.class)
                        .path("document/download")
                        .queryParam("id", neesDoc.getId())
                        .toUriString())
                .setApprovalDocumentState(stageApprovalDocument.getState());

    }

}
