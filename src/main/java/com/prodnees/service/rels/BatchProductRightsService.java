package com.prodnees.service.rels;

import com.prodnees.domain.rels.BatchProductRights;
import java.util.List;
import java.util.Optional;

public interface BatchProductRightsService {
    BatchProductRights save(BatchProductRights batchProductRights);

    Optional<BatchProductRights> findByBatchProductIdAndOwnerId(int batchProductId, int ownerId);

    List<BatchProductRights> getAllByBatchProductId(int batchProductId);

    List<BatchProductRights> getAllByOwnerId(int ownerId);

    boolean hasBatchProductEditorRights(int batchProductId, int editorId);

    boolean hasBatchProductReaderRights(int batchProductId, int readerId);

}
