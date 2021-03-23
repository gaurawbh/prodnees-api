package com.prodnees.service.rels;

import com.prodnees.domain.rels.BatchProductRight;

import java.util.List;
import java.util.Optional;

public interface BatchProductRightService {
    BatchProductRight save(BatchProductRight batchProductRight);

    Optional<BatchProductRight> findByBatchProductIdAndUserId(int batchProductId, int ownerId);

    List<BatchProductRight> getAllByBatchProductId(int batchProductId);

    List<BatchProductRight> getAllByOwnerId(int ownerId);

    boolean hasBatchProductEditorRights(int batchProductId, int editorId);

    boolean hasBatchProductReaderRights(int batchProductId, int readerId);

    void deleteByBatchProductIdAndUserId(int batchProductId, int userId);
}
