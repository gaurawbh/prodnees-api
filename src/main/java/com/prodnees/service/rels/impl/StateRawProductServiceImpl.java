package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.StateRawProductDao;
import com.prodnees.domain.rels.StateRawProduct;
import com.prodnees.service.rels.StateRawProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<StateRawProduct> getByStateIdAndRawProductId(int stateId, int rawProductId) {
        return stateRawProductDao.findByStateIdAndRawProductId(stateId, rawProductId);
    }

    @Override
    public List<StateRawProduct> getAllByStateId(int stateId) {
        return stateRawProductDao.getAllByStateId(stateId);
    }

    @Override
    public List<StateRawProduct> getAllByRawProductId(int rawProductId) {
        return stateRawProductDao.getAllByRawProductId(rawProductId);
    }
}
