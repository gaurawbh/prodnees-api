package com.prodnees.dao.rels;

import com.prodnees.domain.rels.BatchProductRights;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BatchProductRightsDao extends JpaRepository<BatchProductRights, Integer> {
    Optional<BatchProductRights> findByBatchProductIdAndUserId(int batchProductId, int ownerId);

    List<BatchProductRights> getAllByBatchProductId(int batchProductId);

    List<BatchProductRights> getAllByUserId(int ownerId);
}
