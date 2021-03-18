package com.prodnees.dao.rels;

import com.prodnees.domain.rels.BatchProductOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BatchProductOwnerDao extends JpaRepository<BatchProductOwner, Integer> {
    Optional<BatchProductOwner> findByBatchProductIdAndOwnerId(int batchProductId, int ownerId);

    List<BatchProductOwner> getAllByBatchProductId(int batchProductId);

    List<BatchProductOwner> getAllByOwnerId(int ownerId);
}
