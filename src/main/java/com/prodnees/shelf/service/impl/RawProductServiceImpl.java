package com.prodnees.shelf.service.impl;

import com.prodnees.shelf.dao.RawProductDao;
import com.prodnees.shelf.domain.RawMaterial;
import com.prodnees.shelf.service.RawProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawProductServiceImpl implements RawProductService {

    private final RawProductDao rawProductDao;

    public RawProductServiceImpl(RawProductDao rawProductDao) {
        this.rawProductDao = rawProductDao;
    }

    @Override
    public RawMaterial save(RawMaterial rawMaterial) {
        return rawProductDao.save(rawMaterial);
    }

    @Override
    public RawMaterial getById(int id) {
        return rawProductDao.getById(id);
    }

    @Override
    public RawMaterial getByName(String name) {
        return rawProductDao.getByName(name);
    }

    @Override
    public List<RawMaterial> getAllByStageId(int id) {
        return rawProductDao.getAllByStageId(id);
    }
}
