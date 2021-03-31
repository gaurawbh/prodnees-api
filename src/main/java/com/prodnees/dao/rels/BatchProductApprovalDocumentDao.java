package com.prodnees.dao.rels;

import com.prodnees.domain.BatchProductApprovalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BatchProductApprovalDocumentDao extends JpaRepository<BatchProductApprovalDocument, Integer> {
    Optional<BatchProductApprovalDocument> findByBatchProductIdAndDocumentId(int batchProductId, int approvalDocumentId);

    List<BatchProductApprovalDocument> getAllByBatchProductId(int batchProductId);

    List<BatchProductApprovalDocument> getAllByDocumentId(int approvalDocumentId);

}
