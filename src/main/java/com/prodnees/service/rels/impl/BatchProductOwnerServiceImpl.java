package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.BatchProductOwnerDao;
import com.prodnees.domain.rels.BatchProductOwner;
import com.prodnees.service.rels.BatchProductOwnerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatchProductOwnerServiceImpl implements BatchProductOwnerService {
    private final BatchProductOwnerDao batchProductOwnerDao;

    public BatchProductOwnerServiceImpl(BatchProductOwnerDao batchProductOwnerDao) {
        this.batchProductOwnerDao = batchProductOwnerDao;
    }

    @Override
    public BatchProductOwner save(BatchProductOwner batchProductOwner) {
        return batchProductOwnerDao.save(batchProductOwner);
    }

    @Override
    public Optional<BatchProductOwner> getByBatchProductIdAndOwnerId(int batchProductId, int ownerId) {
        return batchProductOwnerDao.findByBatchProductIdAndOwnerId(batchProductId, ownerId);
    }

    @Override
    public List<BatchProductOwner> getAllByBatchProductId(int batchProductId) {
        return batchProductOwnerDao.getAllByBatchProductId(batchProductId);
    }

    @Override
    public List<BatchProductOwner> getAllByOwnerId(int ownerId) {
        return batchProductOwnerDao.getAllByOwnerId(ownerId);
    }
}
