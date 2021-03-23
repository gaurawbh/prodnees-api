package com.prodnees.dao.rels;

import com.prodnees.domain.rels.BatchProductRight;
import com.prodnees.domain.rels.ObjectRightType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BatchProductRightsDao extends JpaRepository<BatchProductRight, Integer> {
    Optional<BatchProductRight> findByBatchProductIdAndUserId(int batchProductId, int userId);

    boolean existsByBatchProductIdAndUserId(int batchProductId, int userId);

    List<BatchProductRight> getAllByBatchProductId(int batchProductId);

    List<BatchProductRight> getAllByUserId(int userId);

    boolean existsByBatchProductIdAndUserIdAndObjectRightType(int batchProductId, int userId, ObjectRightType rightsType);

    void deleteByBatchProductIdAndUserId(int batchProductId, int userId);
}
