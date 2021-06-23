package com.prodnees.core.service.rels.impl;

import com.prodnees.core.dao.rels.BatchRightsDao;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.domain.rels.BatchRight;
import com.prodnees.core.service.rels.BatchRightService;
import com.prodnees.core.web.exception.NeesNotFoundException;
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
    public Optional<BatchRight> findByBatchIdAndUserId(int batchId, int ownerId) {
        return batchRightsDao.findByBatchIdAndUserId(batchId, ownerId);
    }

    @Override
    public BatchRight getByBatchIdAndUserId(int batchId, int ownerId) {
        return findByBatchIdAndUserId(batchId, ownerId)
                .orElseThrow(()-> new NeesNotFoundException(String.format("BatchRight for userId: %d and batchId: %d not found", ownerId, batchId)));
    }

    @Override
    public List<BatchRight> getAllByBatchId(int batchId) {
        return batchRightsDao.getAllByBatchId(batchId);
    }

    @Override
    public List<BatchRight> getAllByUserId(int userId) {
        return batchRightsDao.getAllByUserId(userId);
    }

    /**
     * Check if a user has edit rights to a batch product.
     * <p>Only owners and editors have edit rights</p>
     *
     * @param batchId
     * @param editorId,      @alias userId
     * @return
     */
    @Override
    public boolean hasBatchEditorRights(int batchId, int editorId) {
        return batchRightsDao.existsByBatchIdAndUserIdAndObjectRight(batchId, editorId, ObjectRight.OWNER)
                || batchRightsDao.existsByBatchIdAndUserIdAndObjectRight(batchId, editorId, ObjectRight.EDITOR);
    }

    /**
     * Check if a user has reader rights to a batch product.
     * <p>Owners, editors, and reader have edit rights</p>
     *
     * @param batchId
     * @param readerId,      @alias userId
     * @return
     */
    @Override
    public boolean hasBatchReaderRights(int batchId, int readerId) {
        return batchRightsDao.existsByBatchIdAndUserId(batchId, readerId);
    }

    @Override
    public void deleteByBatchIdAndUserId(int batchId, int userId) {
        batchRightsDao.deleteByBatchIdAndUserId(batchId, userId);
    }
}
