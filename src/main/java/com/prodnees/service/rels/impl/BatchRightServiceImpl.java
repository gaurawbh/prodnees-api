package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.BatchProductRightsDao;
import com.prodnees.domain.enums.ObjectRightType;
import com.prodnees.domain.rels.BatchRight;
import com.prodnees.service.rels.BatchRightService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BatchRightServiceImpl implements BatchRightService {
    private final BatchProductRightsDao batchProductRightsDao;

    public BatchRightServiceImpl(BatchProductRightsDao batchProductRightsDao) {
        this.batchProductRightsDao = batchProductRightsDao;
    }

    @Override
    public BatchRight save(BatchRight batchRight) {
        return batchProductRightsDao.save(batchRight);
    }

    @Override
    public Optional<BatchRight> findByBatchIdAndUserId(int batchProductId, int ownerId) {
        return batchProductRightsDao.findByBatchProductIdAndUserId(batchProductId, ownerId);
    }

    @Override
    public List<BatchRight> getAllByBatchId(int batchProductId) {
        return batchProductRightsDao.getAllByBatchProductId(batchProductId);
    }

    @Override
    public List<BatchRight> getAllByOwnerId(int ownerId) {
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
    public boolean hasBatchEditorRights(int batchProductId, int editorId) {
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
    public boolean hasBatchReaderRights(int batchProductId, int readerId) {
        return batchProductRightsDao.existsByBatchProductIdAndUserId(batchProductId, readerId);
    }

    @Override
    public void deleteByBatchIdAndUserId(int batchProductId, int userId) {
        batchProductRightsDao.deleteByBatchProductIdAndUserId(batchProductId, userId);
    }
}
