package com.prodnees.service.impl;

import com.prodnees.dao.BatchProductDao;
import com.prodnees.domain.BatchProduct;
import com.prodnees.service.BatchProductService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BatchProductServiceImpl implements BatchProductService {
    private final BatchProductDao batchProductDao;

    public BatchProductServiceImpl(BatchProductDao batchProductDao) {
        this.batchProductDao = batchProductDao;
    }

    @Override
    public BatchProduct save(BatchProduct batchProduct) {
        return batchProductDao.save(batchProduct);
    }

    @Override
    public BatchProduct getById(int id) {
        return batchProductDao.getById(id);
    }

    @Override
    public List<BatchProduct> getAllByProductId(int productId) {
        return batchProductDao.getAllByProductId(productId);
    }
}
