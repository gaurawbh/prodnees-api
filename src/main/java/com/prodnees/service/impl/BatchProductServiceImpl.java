package com.prodnees.service.impl;

import com.prodnees.dao.BatchProductDao;
import com.prodnees.domain.BatchProduct;
import com.prodnees.domain.BatchProductStatus;
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

    @Override
    public List<BatchProduct> getAllByIds(Iterable<Integer> batchProductIds) {
        return batchProductDao.findAllById(batchProductIds);
    }

    @Override
    public boolean existsById(int id) {
        return batchProductDao.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        batchProductDao.deleteById(id);
    }

    @Override
    public boolean existsByIdAndStatus(int id, BatchProductStatus status) {
        return batchProductDao.existsByIdAndStatus(id, status);
    }

    @Override
    public List<BatchProduct> getAllByUserIdAndStatus(int userId, BatchProductStatus status) {
        return batchProductDao.getAllByUserIdAndStatus(userId, status.name());
    }
}
