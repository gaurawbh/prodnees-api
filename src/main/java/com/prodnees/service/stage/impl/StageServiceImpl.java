package com.prodnees.service.stage.impl;

import com.prodnees.dao.stage.StageDao;
import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.stage.Stage;
import com.prodnees.service.stage.StageService;
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
    public boolean existsByBatchId(int batchProductId) {
        return stageDao.existsByBatchId(batchProductId);
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
    public List<Stage> getAllByBatchId(int batchProductId) {
        return stageDao.getAllByBatchId(batchProductId);
    }

    @Override
    public List<Stage> getAllByBatchIdAndState(int batchProductId, StageState state) {
        return stageDao.getAllByBatchIdAndStatus(batchProductId, state);
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

}
