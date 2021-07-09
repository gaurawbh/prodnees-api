package com.prodnees.core.service.batch.impl;

import com.prodnees.core.service.batch.RawProductService;
import com.prodnees.shelf.dao.RawProductDao;
import com.prodnees.shelf.domain.RawProduct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawProductServiceImpl implements RawProductService {

    private final RawProductDao rawProductDao;

    public RawProductServiceImpl(RawProductDao rawProductDao) {
        this.rawProductDao = rawProductDao;
    }

    @Override
    public RawProduct save(RawProduct rawProduct) {
        return rawProductDao.save(rawProduct);
    }

    @Override
    public RawProduct getById(int id) {
        return rawProductDao.getById(id);
    }

    @Override
    public RawProduct getByName(String name) {
        return rawProductDao.getByName(name);
    }

    @Override
    public List<RawProduct> getAllByStageId(int id) {
        return rawProductDao.getAllByStageId(id);
    }
}
