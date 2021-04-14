package com.prodnees.dao.rels;

import com.prodnees.domain.enums.ObjectRightType;
import com.prodnees.domain.rels.BatchRight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BatchProductRightsDao extends JpaRepository<BatchRight, Integer> {
    Optional<BatchRight> findByBatchProductIdAndUserId(int batchProductId, int userId);

    boolean existsByBatchProductIdAndUserId(int batchProductId, int userId);

    List<BatchRight> getAllByBatchProductId(int batchProductId);

    List<BatchRight> getAllByUserId(int userId);

    boolean existsByBatchProductIdAndUserIdAndObjectRightType(int batchProductId, int userId, ObjectRightType rightsType);

    void deleteByBatchProductIdAndUserId(int batchProductId, int userId);
}
