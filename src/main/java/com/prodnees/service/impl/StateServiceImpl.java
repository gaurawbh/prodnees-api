package com.prodnees.service.impl;

import com.prodnees.dao.StateDao;
import com.prodnees.domain.State;
import com.prodnees.service.StateService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {
    private final StateDao stateDao;

    public StateServiceImpl(StateDao stateDao) {
        this.stateDao = stateDao;
    }

    @Override
    public boolean existsByBatchProductId(int batchProductId) {
        return stateDao.existsByBatchProductId(batchProductId);
    }

    @Override
    public State save(State state) {
        return stateDao.save(state);
    }

    @Override
    public State getById(int id) {
        return stateDao.getById(id);
    }

    @Override
    public State getByName(String name) {
        return stateDao.getByName(name);
    }

    @Override
    public List<State> getAllByBatchProductId(int batchProductId) {
        return stateDao.getAllByBatchProductId(batchProductId);
    }

    @Override
    public List<State> getAllByBatchProductIdAndComplete(int batchProductId, boolean isComplete) {
        return stateDao.getAllByBatchProductIdAndComplete(batchProductId, isComplete);
    }

    @Override
    public void deleteById(int id) {
        stateDao.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return stateDao.existsById(id);
    }

    @Override
    public Optional<State> findById(int id) {
        return stateDao.findById(id);
    }
}
