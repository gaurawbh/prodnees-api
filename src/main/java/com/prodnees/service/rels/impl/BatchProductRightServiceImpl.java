package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.BatchProductRightsDao;
import com.prodnees.domain.rels.BatchProductRight;
import com.prodnees.domain.rels.ObjectRightType;
import com.prodnees.service.rels.BatchProductRightService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BatchProductRightServiceImpl implements BatchProductRightService {
    private final BatchProductRightsDao batchProductRightsDao;

    public BatchProductRightServiceImpl(BatchProductRightsDao batchProductRightsDao) {
        this.batchProductRightsDao = batchProductRightsDao;
    }

    @Override
    public BatchProductRight save(BatchProductRight batchProductRight) {
        return batchProductRightsDao.save(batchProductRight);
    }

    @Override
    public Optional<BatchProductRight> findByBatchProductIdAndUserId(int batchProductId, int ownerId) {
        return batchProductRightsDao.findByBatchProductIdAndUserId(batchProductId, ownerId);
    }

    @Override
    public List<BatchProductRight> getAllByBatchProductId(int batchProductId) {
        return batchProductRightsDao.getAllByBatchProductId(batchProductId);
    }

    @Override
    public List<BatchProductRight> getAllByOwnerId(int ownerId) {
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

        return batchProductRightsDao.existsByBatchProductIdAndUserIdAndObjectRightType(batchProductId, editorId, ObjectRightType.OWNER)
                || batchProductRightsDao.existsByBatchProductIdAndUserIdAndObjectRightType(batchProductId, editorId, ObjectRightType.EDITOR);
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

    @Override
    public void deleteByBatchProductIdAndUserId(int batchProductId, int userId) {
        batchProductRightsDao.deleteByBatchProductIdAndUserId(batchProductId, userId);
    }
}
