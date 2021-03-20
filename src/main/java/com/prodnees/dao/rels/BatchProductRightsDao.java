package com.prodnees.dao.rels;

import com.prodnees.domain.rels.BatchProductRights;
import com.prodnees.domain.rels.ObjectRightsType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BatchProductRightsDao extends JpaRepository<BatchProductRights, Integer> {
    Optional<BatchProductRights> findByBatchProductIdAndUserId(int batchProductId, int userId);

    boolean existsByBatchProductIdAndUserId(int batchProductId, int userId);

    List<BatchProductRights> getAllByBatchProductId(int batchProductId);

    List<BatchProductRights> getAllByUserId(int userId);

    boolean existsByBatchProductIdAndUserIdAndObjectRightsType(int batchProductId, int userId, ObjectRightsType rightsType);
}
