package com.prodnees.service.rels;

import com.prodnees.domain.rels.BatchProductOwner;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BatchProductOwnerService {
    BatchProductOwner save(BatchProductOwner batchProductOwner);

    BatchProductOwner getByBatchProductIdAndOwnerId(int batchProductId, int ownerId);

    List<BatchProductOwner> getAllByBatchProductId(int batchProductId);

    List<BatchProductOwner> getAllByOwnerId(int batchProductId);

}
