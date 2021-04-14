package com.prodnees.service.batchproduct.impl;

import com.prodnees.dao.batchproduct.BatchDao;
import com.prodnees.domain.batchproduct.Batch;
import com.prodnees.domain.enums.BatchStatus;
import com.prodnees.service.batchproduct.BatchService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BatchServiceImpl implements BatchService {
    private final BatchDao batchDao;

    public BatchServiceImpl(BatchDao batchDao) {
        this.batchDao = batchDao;
    }

    @Override
    public Batch save(Batch batch) {
        return batchDao.save(batch);
    }

    @Override
    public Batch getById(int id) {
        return batchDao.getById(id);
    }

    @Override
    public List<Batch> getAllByProductId(int productId) {
        return batchDao.getAllByProductId(productId);
    }

    @Override
    public List<Batch> getAllByIds(Iterable<Integer> ids) {
        return batchDao.findAllById(ids);
    }

    @Override
    public boolean existsById(int id) {
        return batchDao.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        batchDao.deleteById(id);
    }

    @Override
    public boolean existsByIdAndStatus(int id, BatchStatus status) {
        return batchDao.existsByIdAndStatus(id, status);
    }

    @Override
    public List<Batch> getAllByUserIdAndStatus(int userId, BatchStatus status) {
        return batchDao.getAllByUserIdAndStatus(userId, status.name());
    }
}
