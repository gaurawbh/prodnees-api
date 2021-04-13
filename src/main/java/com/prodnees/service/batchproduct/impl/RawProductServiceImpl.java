package com.prodnees.service.batchproduct.impl;

import com.prodnees.dao.batchproduct.RawProductDao;
import com.prodnees.domain.batchproduct.RawProduct;
import com.prodnees.service.batchproduct.RawProductService;
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
    public List<RawProduct> getAllByStateId(int id) {
        return rawProductDao.getAllByStateId(id);
    }
}
