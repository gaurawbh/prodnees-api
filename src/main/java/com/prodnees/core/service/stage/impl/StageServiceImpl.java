package com.prodnees.core.service.stage.impl;

import com.prodnees.auth.config.tenancy.CurrentTenantResolver;
import com.prodnees.core.dao.stage.StageDao;
import com.prodnees.core.domain.enums.StageState;
import com.prodnees.core.domain.stage.Stage;
import com.prodnees.core.service.stage.StageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StageServiceImpl implements StageService {
    private final StageDao stageDao;

    public StageServiceImpl(StageDao stageDao) {
        this.stageDao = stageDao;
    }

    @Override
    public boolean existsByBatchId(int batchId) {
        return stageDao.existsByBatchId(batchId);
    }

    @Override
    public Stage save(Stage stage) {
        return stageDao.save(stage);
    }

    @Override
    public Stage getById(int id) {
        return stageDao.getById(id);
    }

    @Override
    public Stage getByName(String name) {
        return stageDao.getByName(name);
    }

    @Override
    public List<Stage> getAllByBatchId(int batchId) {
        return stageDao.getAllByBatchId(batchId);
    }

    @Override
    public List<Stage> getAllByBatchIdAndState(int batchId, StageState state) {
        return stageDao.getAllByBatchIdAndState(batchId, state);
    }

    @Override
    public void deleteById(int id) {
        stageDao.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return stageDao.existsById(id);
    }

    @Override
    public Optional<Stage> findById(int id) {
        return stageDao.findById(id);
    }

    @Override
    public int getNextId() {
        return stageDao.getNextId(CurrentTenantResolver.getTenant(), "stage", "id");
    }
}
