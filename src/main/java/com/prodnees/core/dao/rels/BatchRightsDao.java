package com.prodnees.core.dao.rels;

import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.domain.rels.BatchRight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BatchRightsDao extends JpaRepository<BatchRight, Integer> {
    Optional<BatchRight> findByBatchIdAndUserId(int batchId, int userId);

    boolean existsByBatchIdAndUserId(int batchId, int userId);

    List<BatchRight> getAllByBatchId(int batchId);

    List<BatchRight> getAllByUserId(int userId);

    boolean existsByBatchIdAndUserIdAndObjectRight(int batchId, int userId, ObjectRight objectRight);

    void deleteByBatchIdAndUserId(int batchId, int userId);
}
