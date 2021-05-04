package com.prodnees.action.stage.impl;

import com.prodnees.action.stage.StageTodoAction;
import com.prodnees.domain.stage.StageTodo;
import com.prodnees.service.stage.StageTodoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StageTodoActionImpl implements StageTodoAction {
    private final StageTodoService stageTodoService;

    public StageTodoActionImpl(StageTodoService stageTodoService) {
        this.stageTodoService = stageTodoService;
    }

    @Override
    public boolean existsByBatchId(int batchId) {
        return stageTodoService.existsByBatchId(batchId);
    }

    @Override
    public StageTodo save(StageTodo stageTodo) {
        return stageTodoService.save(stageTodo);
    }

    @Override
    public StageTodo getById(int id) {
        return stageTodoService.getById(id);
    }

    @Override
    public List<StageTodo> getAllByBatchId(int batchId) {
        return stageTodoService.getAllByBatchId(batchId);
    }

    @Override
    public List<StageTodo> getAllByStageId(int stageId) {
        return stageTodoService.getAllByStageId(stageId);
    }

    @Override
    public Optional<StageTodo> findById(int id) {
        return stageTodoService.findById(id);
    }

    @Override
    public void deleteById(int id) {
        stageTodoService.deleteById(id);
    }

}
