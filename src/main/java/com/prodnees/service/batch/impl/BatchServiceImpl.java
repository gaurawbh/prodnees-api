package com.prodnees.service.batch.impl;

import com.prodnees.auth.config.tenancy.CurrentTenantResolver;
import com.prodnees.dao.batchproduct.BatchDao;
import com.prodnees.domain.batch.Batch;
import com.prodnees.domain.enums.BatchState;
import com.prodnees.service.batch.BatchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Batch> findById(int id) {
        return batchDao.findById(id);
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
    public boolean existsByIdAndState(int id, BatchState state) {
        return batchDao.existsByIdAndState(id, state);
    }

    @Override
    public List<Batch> getAllByUserIdAndState(int userId, BatchState state) {
        return batchDao.getAllByUserIdAndState(userId, state.name());
    }

    @Override
    public int getNextId() {
        return batchDao.getNextId(CurrentTenantResolver.getTenant(), "batch", "id");
    }
}
