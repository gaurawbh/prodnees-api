package com.prodnees.service.impl;

import com.prodnees.dao.StateDao;
import com.prodnees.domain.State;
import com.prodnees.service.StateService;
import org.springframework.stereotype.Service;

@Service
public class StateServiceImpl implements StateService {
    private final StateDao stateDao;

    public StateServiceImpl(StateDao stateDao) {
        this.stateDao = stateDao;
    }

    @Override
    public State save(State state) {
        return stateDao.save(state);
    }

    @Override
    public State getById(int id) {
        return null;
    }

    @Override
    public State getByBatchProductId(int batchProductId) {
        return null;
    }
}
