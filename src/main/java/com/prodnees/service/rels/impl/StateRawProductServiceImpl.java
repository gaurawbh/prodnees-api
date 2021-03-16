package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.StateRawProductDao;
import com.prodnees.domain.rels.StateRawProduct;
import com.prodnees.service.rels.StateRawProductService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StateRawProductServiceImpl implements StateRawProductService {
    private final StateRawProductDao stateRawProductDao;

    public StateRawProductServiceImpl(StateRawProductDao stateRawProductDao) {
        this.stateRawProductDao = stateRawProductDao;
    }

    @Override
    public StateRawProduct save(StateRawProduct stateRawProduct) {
        return stateRawProductDao.save(stateRawProduct);
    }

    @Override
    public StateRawProduct getByStateIdAndRawProductId(int stateId, int rawProductId) {
        return null;
    }

    @Override
    public List<StateRawProduct> getAllByStateId(int stateId) {
        return null;
    }

    @Override
    public List<StateRawProduct> getAllByRawProductId(int stateId) {
        return null;
    }
}
