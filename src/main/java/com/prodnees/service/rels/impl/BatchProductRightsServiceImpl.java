package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.BatchProductRightsDao;
import com.prodnees.domain.rels.BatchProductRights;
import com.prodnees.domain.rels.ObjectRightsType;
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
    public Optional<BatchProductRights> findByBatchProductIdAndOwnerId(int batchProductId, int ownerId) {
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

    /**
     * Check if a user has edit rights to a batch product.
     * <p>Only owners and editors have edit rights</p>
     *
     * @param batchProductId
     * @param editorId,      @alias userId
     * @return
     */
    @Override
    public boolean hasBatchProductEditorRights(int batchProductId, int editorId) {

        return batchProductRightsDao.existsByBatchProductIdAndUserIdAndObjectRightsType(batchProductId, editorId, ObjectRightsType.OWNER)
                || batchProductRightsDao.existsByBatchProductIdAndUserIdAndObjectRightsType(batchProductId, editorId, ObjectRightsType.EDITOR);
    }

    /**
     * Check if a user has reader rights to a batch product.
     * <p>Owners, editors, and reader have edit rights</p>
     *
     * @param batchProductId
     * @param readerId,      @alias userId
     * @return
     */
    @Override
    public boolean hasBatchProductReaderRights(int batchProductId, int readerId) {
        return batchProductRightsDao.existsByBatchProductIdAndUserId(batchProductId, readerId);
    }
}
