package com.prodnees.core.service.batch.impl;

import com.prodnees.auth.config.tenancy.CurrentTenantResolver;
import com.prodnees.core.dao.batch.BatchDao;
import com.prodnees.core.domain.batch.Batch;
import com.prodnees.core.domain.enums.BatchState;
import com.prodnees.core.service.batch.BatchService;
import com.prodnees.core.web.exception.NeesNotFoundException;
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
        return batchDao.findById(id)
                .orElseThrow(() -> new NeesNotFoundException(String.format("Batch with id: %d not found", id)));

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

    @Override
    public List<Batch> findAll() {
        return batchDao.findAll();
    }
}
