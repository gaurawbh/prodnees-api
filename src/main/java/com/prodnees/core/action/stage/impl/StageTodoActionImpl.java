package com.prodnees.core.action.stage.impl;

import com.prodnees.auth.filter.RequestContext;
import com.prodnees.core.action.stage.StageTodoAction;
import com.prodnees.core.domain.stage.StageTodo;
import com.prodnees.core.domain.user.NeesObject;
import com.prodnees.core.dto.stage.StageTodoDto;
import com.prodnees.core.service.stage.StageTodoService;
import com.prodnees.core.service.user.NeesObjectRightService;
import com.prodnees.core.util.LocalAssert;
import com.prodnees.core.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StageTodoActionImpl implements StageTodoAction {
    private final StageTodoService stageTodoService;
    private final NeesObjectRightService neesObjectRightService;

    public StageTodoActionImpl(StageTodoService stageTodoService,
                               NeesObjectRightService neesObjectRightService) {
        this.stageTodoService = stageTodoService;
        this.neesObjectRightService = neesObjectRightService;
    }

    @Override
    public boolean existsByBatchId(int batchId) {
        return stageTodoService.existsByBatchId(batchId);
    }

    @Override
    public StageTodo addNew(StageTodoDto dto, int batchId) {
        verifyUpdateRights();
        StageTodo stageTodo = MapperUtil.getDozer().map(dto, StageTodo.class)
                .setBatchId(batchId)
                .setComplete(false);
        return stageTodoService.save(stageTodo);

    }

    @Override
    public StageTodo getById(int id) {
        verifyViewRights();
        return stageTodoService.getById(id);
    }

    @Override
    public List<StageTodo> getAllByBatchId(int batchId) {
        verifyViewRights();
        return stageTodoService.getAllByBatchId(batchId);
    }

    @Override
    public List<StageTodo> getAllByStageId(int stageId) {
        verifyViewRights();
        return stageTodoService.getAllByStageId(stageId);
    }
    @Override
    public void deleteById(int id) {
        verifyFullRights();
        stageTodoService.deleteById(id);
    }

    private void verifyViewRights() {
        LocalAssert.isTrue(neesObjectRightService.hasViewObjectRight(RequestContext.getUserId(), NeesObject.batch), "Insufficient right to view Stage Todo");
    }

    private void verifyUpdateRights() {
        LocalAssert.isTrue(neesObjectRightService.hasUpdateObjectRight(RequestContext.getUserId(), NeesObject.batch), "Insufficient right to Add or Update Stage Todo");

    }

    private void verifyFullRights() {
        LocalAssert.isTrue(neesObjectRightService.hasFullObjectRight(RequestContext.getUserId(), NeesObject.batch), "Insufficient right to delete Stage Todo");

    }

}
