package com.prodnees.service.stage.impl;

import com.prodnees.dao.stage.StageTodoDao;
import com.prodnees.domain.stage.StageTodo;
import com.prodnees.service.stage.StageTodoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StageTodoServiceImpl implements StageTodoService {
    private final StageTodoDao stageTodoDao;

    public StageTodoServiceImpl(StageTodoDao stageTodoDao) {
        this.stageTodoDao = stageTodoDao;
    }

    @Override
    public boolean existsByBatchId(int batchId) {
        return stageTodoDao.existsByBatchId(batchId);
    }

    @Override
    public StageTodo save(StageTodo stageTodo) {
        return stageTodoDao.save(stageTodo);
    }

    @Override
    public StageTodo getById(int id) {
        return stageTodoDao.getById(id);
    }

    @Override
    public List<StageTodo> getAllByBatchId(int batchId) {
        return stageTodoDao.getAllByBatchId(batchId);
    }

    @Override
    public List<StageTodo> getAllByStageId(int stageId) {
        return stageTodoDao.getAllByStageId(stageId);
    }

    @Override
    public StageTodo getByBatchIdAndName(int batchId, String name) {
        return stageTodoDao.getByBatchIdAndName(batchId, name);
    }

    @Override
    public Optional<StageTodo> findById(int id) {
        return stageTodoDao.findById(id);
    }
}
