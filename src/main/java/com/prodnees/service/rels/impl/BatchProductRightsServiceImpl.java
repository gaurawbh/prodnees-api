package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.BatchProductRightsDao;
import com.prodnees.domain.rels.BatchProductRights;
import com.prodnees.service.rels.BatchProductRightsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatchProductRightsServiceImpl implements BatchProductRightsService {
    private final BatchProductRightsDao batchProductRightsDao;

    public BatchProductRightsServiceImpl(BatchProductRightsDao batchProductRightsDao) {
        this.batchProductRightsDao = batchProductRightsDao;
    }

    @Override
    public BatchProductRights save(BatchProductRights batchProductRights) {
        return batchProductRightsDao.save(batchProductRights);
    }

    @Override
    public Optional<BatchProductRights> getByBatchProductIdAndOwnerId(int batchProductId, int ownerId) {
        return batchProductRightsDao.findByBatchProductIdAndUserId(batchProductId, ownerId);
    }

    @Override
    public List<BatchProductRights> getAllByBatchProductId(int batchProductId) {
        return batchProductRightsDao.getAllByBatchProductId(batchProductId);
    }

    @Override
    public List<BatchProductRights> getAllByOwnerId(int ownerId) {
        return batchProductRightsDao.getAllByUserId(ownerId);
    }
}
