package com.prodnees.action.stage.impl;

import com.prodnees.action.StageList;
import com.prodnees.action.stage.StageAction;
import com.prodnees.controller.DocumentController;
import com.prodnees.domain.Document;
import com.prodnees.domain.batch.RawProduct;
import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.stage.Stage;
import com.prodnees.domain.stage.StageApprovalDocument;
import com.prodnees.domain.stage.StageTodo;
import com.prodnees.dto.stage.StageDto;
import com.prodnees.model.stage.StageApprovalDocumentModel;
import com.prodnees.model.stage.StageModel;
import com.prodnees.service.DocumentService;
import com.prodnees.service.batch.RawProductService;
import com.prodnees.service.rels.BatchRightService;
import com.prodnees.service.rels.StageApprovalDocumentService;
import com.prodnees.service.stage.StageService;
import com.prodnees.service.stage.StageTodoService;
import com.prodnees.util.ValidatorUtil;
import com.prodnees.web.exception.NeesNotFoundException;
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
    private final DocumentService documentService;
    private final BatchRightService batchRightService;
    private final StageList stageList;


    public StageActionImpl(StageService stageService,
                           StageApprovalDocumentService stageApprovalDocumentService,
                           StageTodoService stageTodoService,
                           RawProductService rawProductService,
                           DocumentService documentService,
                           BatchRightService batchRightService,
                           StageList stageList) {
        this.stageService = stageService;
        this.stageApprovalDocumentService = stageApprovalDocumentService;
        this.stageTodoService = stageTodoService;
        this.rawProductService = rawProductService;
        this.documentService = documentService;
        this.batchRightService = batchRightService;
        this.stageList = stageList;
    }

    @Override
    public boolean existsByBatchId(int batchProductId) {
        return stageService.existsByBatchId(batchProductId);
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
     if no {@link Stage#getIndx()} is provided in the dto, it goes on top of the stack
     *
     * @param dto
     * @return
     */
    @Override
    public StageModel addNew(StageDto dto) {
        Stage stage = new Stage()
                .setBatchId(dto.getBatchId())
                .setIndx(ValidatorUtil.ifValidIntegerOrElse(dto.getIndex(), -1))
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
        return stageService.findById(id).orElseThrow(()-> new NeesNotFoundException(String.format("Stage with id: %d not found", id)));
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
    public List<StageModel> getAllByBatchId(int batchProductId) {
        List<Stage> stageList = stageService.getAllByBatchId(batchProductId);
        List<StageModel> stageModelList = new ArrayList<>();
        stageList.forEach(state -> stageModelList.add(entityToModel(state)));
        return stageModelList;
    }

    @Override
    public List<Stage> getAllByBatchIdAndState(int batchProductId, StageState state) {
        return stageService.getAllByBatchIdAndState(batchProductId, state);
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
        Document document = documentService.getById(stageApprovalDocument.getDocumentId());

        return new StageApprovalDocumentModel()
                .setId(stageApprovalDocument.getId())
                .setName(document.getName())
                .setDocumentId(stageApprovalDocument.getDocumentId())
                .setApproverId(stageApprovalDocument.getApproverId())
                .setApproverEmail(stageApprovalDocument.getApproverEmail())
                .setDocumentUrl(MvcUriComponentsBuilder.fromController(DocumentController.class)
                        .path("document/load")
                        .queryParam("id", document.getId())
                        .toUriString())
                .setDocumentDownloadUrl(MvcUriComponentsBuilder.fromController(DocumentController.class)
                        .path("document/download")
                        .queryParam("id", document.getId())
                        .toUriString())
                .setApprovalDocumentState(stageApprovalDocument.getState());

    }

}
