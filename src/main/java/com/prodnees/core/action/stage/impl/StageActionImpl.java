package com.prodnees.core.action.stage.impl;

import com.prodnees.auth.filter.RequestContext;
import com.prodnees.core.action.StageList;
import com.prodnees.core.action.stage.StageAction;
import com.prodnees.core.domain.enums.StageState;
import com.prodnees.core.domain.stage.Stage;
import com.prodnees.core.domain.stage.StageTodo;
import com.prodnees.core.domain.user.NeesObject;
import com.prodnees.core.dto.stage.StageDto;
import com.prodnees.core.model.stage.StageModel;
import com.prodnees.core.service.NeesDocumentService;
import com.prodnees.core.service.batch.RawProductService;
import com.prodnees.core.service.stage.StageService;
import com.prodnees.core.service.stage.StageTodoService;
import com.prodnees.core.service.user.NeesObjectRightService;
import com.prodnees.core.util.LocalAssert;
import com.prodnees.core.web.exception.NeesNotFoundException;
import com.prodnees.shelf.domain.RawProduct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StageActionImpl implements StageAction {

    private final StageService stageService;
    private final StageTodoService stageTodoService;
    private final RawProductService rawProductService;
    private final NeesDocumentService neesDocumentService;
    private final StageList stageList;
    private final NeesObjectRightService neesObjectRightService;

    public StageActionImpl(StageService stageService,
                           StageTodoService stageTodoService,
                           RawProductService rawProductService,
                           NeesDocumentService neesDocumentService,
                           StageList stageList,
                           NeesObjectRightService neesObjectRightService) {
        this.stageService = stageService;
        this.stageTodoService = stageTodoService;
        this.rawProductService = rawProductService;
        this.neesDocumentService = neesDocumentService;
        this.stageList = stageList;
        this.neesObjectRightService = neesObjectRightService;
    }

    @Override
    public boolean existsByBatchId(int batchId) {
        return stageService.existsByBatchId(batchId);
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
        List<StageTodo> stageTodoList = stageTodoService.getAllByStageId(stage.getId());
        List<RawProduct> rawProductList = rawProductService.getAllByStageId(stage.getId());
        stageModel.setId(stage.getId())
                .setBatchId(stage.getBatchId())
                .setIndex(stage.getIndx())
                .setName(stage.getName())
                .setDescription(stage.getDescription())
                .setStageTodos(stageTodoList)
                .setRawProducts(rawProductList)
                .setStatus(stage.getState());
        return stageModel;
    }

    private void verifyViewRights() {
        LocalAssert.isTrue(neesObjectRightService.hasViewObjectRight(RequestContext.getUserId(), NeesObject.batch), "Insufficient right to view Batch");
    }

    private void verifyUpdateRights() {
        LocalAssert.isTrue(neesObjectRightService.hasUpdateObjectRight(RequestContext.getUserId(), NeesObject.batch), "Insufficient right to add or update Batch");

    }

    private void verifyFullRights() {
        LocalAssert.isTrue(neesObjectRightService.hasFullObjectRight(RequestContext.getUserId(), NeesObject.batch), "Insufficient right to delete Batch");

    }

}
