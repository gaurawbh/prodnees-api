package com.prodnees.service.rels;

import com.prodnees.domain.rels.BatchRight;
import java.util.List;
import java.util.Optional;

public interface BatchProductRightService {
    BatchRight save(BatchRight batchRight);

    Optional<BatchRight> findByBatchProductIdAndUserId(int batchProductId, int ownerId);

    List<BatchRight> getAllByBatchProductId(int batchProductId);

    List<BatchRight> getAllByOwnerId(int ownerId);

    boolean hasBatchProductEditorRights(int batchProductId, int editorId);

    boolean hasBatchProductReaderRights(int batchProductId, int readerId);

    void deleteByBatchProductIdAndUserId(int batchProductId, int userId);
}
