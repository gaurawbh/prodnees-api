package com.prodnees.action.stage.impl;

import com.prodnees.action.BatchStageList;
import com.prodnees.action.stage.StageAction;
import com.prodnees.controller.DocumentController;
import com.prodnees.domain.Document;
import com.prodnees.domain.batch.RawProduct;
import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.stage.Stage;
import com.prodnees.domain.stage.StageApprovalDocument;
import com.prodnees.domain.stage.StageTodo;
import com.prodnees.model.RawProductModel;
import com.prodnees.model.stage.StageApprovalDocumentModel;
import com.prodnees.model.stage.StageModel;
import com.prodnees.model.stage.StageTodoModel;
import com.prodnees.service.DocumentService;
import com.prodnees.service.batch.RawProductService;
import com.prodnees.service.rels.BatchRightService;
import com.prodnees.service.rels.StageApprovalDocumentService;
import com.prodnees.service.stage.StageService;
import com.prodnees.service.stage.StageTodoService;
import com.prodnees.util.MapperUtil;
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
    private final BatchStageList batchStageList;


    public StageActionImpl(StageService stageService,
                           StageApprovalDocumentService stageApprovalDocumentService,
                           StageTodoService stageTodoService,
                           RawProductService rawProductService,
                           DocumentService documentService,
                           BatchRightService batchRightService,
                           BatchStageList batchStageList) {
        this.stageService = stageService;
        this.stageApprovalDocumentService = stageApprovalDocumentService;
        this.stageTodoService = stageTodoService;
        this.rawProductService = rawProductService;
        this.documentService = documentService;
        this.batchRightService = batchRightService;
        this.batchStageList = batchStageList;
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
     * State must be validated before calling save method
     * <p>Last State Validated</p>
     * <p>Next State Validated</p>
     *
     * @param stage
     * @return
     */
    @Override
    public StageModel save(Stage stage) {
        if (stage.getName().isBlank()) {
            stage.setName(String.format("State-%d-%d", stage.getBatchId(), batchStageList.size(stage.getBatchId())));
        }
        return entityToModel(batchStageList.add(stage));
    }

    @Override
    public Optional<Stage> findById(int id) {
        return stageService.findById(id);
    }

    @Override
    public Stage getById(int id) {
        return stageService.getById(id);
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
        batchStageList.remove(stage);
    }

    private StageModel entityToModel(Stage stage) {
        StageModel stageModel = new StageModel();
        List<StageApprovalDocument> stageApprovalDocumentList = stageApprovalDocumentService.getAllByStageId(stage.getId());
        List<StageTodo> stageTodoList = stageTodoService.getAllByStageId(stage.getId());
        List<RawProduct> rawProductList = rawProductService.getAllByStageId(stage.getId());
        List<StageApprovalDocumentModel> stageApprovalDocumentModelList = new ArrayList<>();
        stageApprovalDocumentList.forEach(stateApprovalDocument -> stageApprovalDocumentModelList.add(entityToModel(stateApprovalDocument)));
        List<StageTodoModel> stageTodoModelList = new ArrayList<>();
        stageTodoList.forEach(stageTodo -> stageTodoModelList.add(entityToModel(stageTodo)));
        List<RawProductModel> rawProductModelList = new ArrayList<>();
        rawProductList.forEach(rawProduct -> rawProductModelList.add(entityToModel(rawProduct)));
        stageModel.setId(stage.getId())
                .setBatchId(stage.getBatchId())
                .setIndex(stage.getIndex())
                .setName(stage.getName())
                .setDescription(stage.getDescription())
                .setApprovalDocuments(stageApprovalDocumentModelList)
                .setStageTodoList(stageTodoModelList)
                .setRawProductList(rawProductModelList)
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

    private StageTodoModel entityToModel(StageTodo stageTodo) {
        return MapperUtil.getDozer().map(stageTodo, StageTodoModel.class);
    }

    private RawProductModel entityToModel(RawProduct rawProduct) {
        return MapperUtil.getDozer().map(rawProduct, RawProductModel.class);

    }
}
