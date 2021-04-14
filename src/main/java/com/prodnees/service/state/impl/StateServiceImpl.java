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
    public boolean existsByBatchId(int batchProductId) {
        return stateDao.existsByBatchId(batchProductId);
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
    public List<State> getAllByBatchId(int batchProductId) {
        return stateDao.getAllByBatchId(batchProductId);
    }

    @Override
    public List<State> getAllByBatchIdAndStatus(int batchProductId, StateStatus status) {
        return stateDao.getAllByBatchIdAndStatus(batchProductId, status);
    }

    @Override
    public List<State> getAllByBatchIdAndIndexGreaterThan(int batchId, int index) {
        return stateDao.getAllByBatchIdAndIndexGreaterThan(batchId, index);
    }

    @Override
    public List<State> getAllByBatchIdAndIndexLessThan(int batchId, int index) {
        return stateDao.getAllByBatchIdAndIndexLessThan(batchId, index);
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

    @Override
    public int countByBatchId(int batchId) {
        return stateDao.countByBatchId(batchId);
    }

    @Override
    public boolean existsByBatchIdAndIndex(int batchId, int i) {
        return stateDao.existsByBatchIdAndIndex(batchId, i);
    }

    @Override
    public State getByBatchIdAndIndex(int batchId, int i) {
        return stateDao.getByBatchIdAndIndex(batchId, i);
    }

    @Override
    public List<State> saveAll(List<State> stateList) {
        return stateDao.saveAll(stateList);
    }
}
