package com.prodnees.service.rels;

import com.prodnees.domain.rels.BatchRight;
import java.util.List;
import java.util.Optional;

public interface BatchRightService {
    BatchRight save(BatchRight batchRight);

    Optional<BatchRight> findByBatchIdAndUserId(int batchId, int ownerId);

    List<BatchRight> getAllByBatchId(int batchId);

    List<BatchRight> getAllByOwnerId(int ownerId);

    boolean hasBatchEditorRights(int batchId, int editorId);

    boolean hasBatchReaderRights(int batchId, int readerId);

    void deleteByBatchIdAndUserId(int batchId, int userId);
}
