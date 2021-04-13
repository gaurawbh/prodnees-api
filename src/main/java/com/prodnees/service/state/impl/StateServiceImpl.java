package com.prodnees.service.state.impl;

import com.prodnees.dao.state.StateDao;
import com.prodnees.domain.enums.StateStatus;
import com.prodnees.domain.state.State;
import com.prodnees.service.state.StateService;
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
    public List<State> getAllByBatchProductIdAndStatus(int batchProductId, StateStatus status) {
        return stateDao.getAllByBatchProductIdAndStatus(batchProductId, status);
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
