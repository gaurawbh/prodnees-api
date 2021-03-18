package com.prodnees.service.rels;

import com.prodnees.domain.rels.BatchProductOwner;

import java.util.List;
import java.util.Optional;

public interface BatchProductOwnerService {
    BatchProductOwner save(BatchProductOwner batchProductOwner);

    Optional<BatchProductOwner> getByBatchProductIdAndOwnerId(int batchProductId, int ownerId);

    List<BatchProductOwner> getAllByBatchProductId(int batchProductId);

    List<BatchProductOwner> getAllByOwnerId(int ownerId);

}
