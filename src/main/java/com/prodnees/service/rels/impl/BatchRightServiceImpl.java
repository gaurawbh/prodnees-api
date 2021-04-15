package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.BatchRightsDao;
import com.prodnees.domain.enums.ObjectRight;
import com.prodnees.domain.rels.BatchRight;
import com.prodnees.service.rels.BatchRightService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BatchRightServiceImpl implements BatchRightService {
    private final BatchRightsDao batchRightsDao;

    public BatchRightServiceImpl(BatchRightsDao batchRightsDao) {
        this.batchRightsDao = batchRightsDao;
    }

    @Override
    public BatchRight save(BatchRight batchRight) {
        return batchRightsDao.save(batchRight);
    }

    @Override
    public Optional<BatchRight> findByBatchIdAndUserId(int batchProductId, int ownerId) {
        return batchRightsDao.findByBatchIdAndUserId(batchProductId, ownerId);
    }

    @Override
    public List<BatchRight> getAllByBatchId(int batchProductId) {
        return batchRightsDao.getAllByBatchId(batchProductId);
    }

    @Override
    public List<BatchRight> getAllByUserId(int userId) {
        return batchRightsDao.getAllByUserId(userId);
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
        return batchRightsDao.existsByBatchIdAndUserIdAndObjectRight(batchProductId, editorId, ObjectRight.OWNER)
                || batchRightsDao.existsByBatchIdAndUserIdAndObjectRight(batchProductId, editorId, ObjectRight.EDITOR);
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
        return batchRightsDao.existsByBatchIdAndUserId(batchProductId, readerId);
    }

    @Override
    public void deleteByBatchIdAndUserId(int batchProductId, int userId) {
        batchRightsDao.deleteByBatchIdAndUserId(batchProductId, userId);
    }
}
