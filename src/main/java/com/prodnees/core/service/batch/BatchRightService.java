package com.prodnees.core.service.batch;

import com.prodnees.core.domain.rels.BatchRight;

import java.util.List;
import java.util.Optional;

public interface BatchRightService {
    BatchRight save(BatchRight batchRight);

    Optional<BatchRight> findByBatchIdAndUserId(int batchId, int ownerId);

    BatchRight getByBatchIdAndUserId(int batchId, int ownerId);

    List<BatchRight> getAllByBatchId(int batchId);

    List<BatchRight> getAllByUserId(int userId);

    boolean hasBatchEditorRights(int batchId, int editorId);

    boolean hasBatchReaderRights(int batchId, int readerId);

    void deleteByBatchIdAndUserId(int batchId, int userId);
}
