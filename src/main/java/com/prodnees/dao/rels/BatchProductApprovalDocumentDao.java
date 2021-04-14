package com.prodnees.dao.rels;

import com.prodnees.domain.batch.BatchApprovalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BatchProductApprovalDocumentDao extends JpaRepository<BatchApprovalDocument, Integer> {
    Optional<BatchApprovalDocument> findByBatchProductIdAndDocumentId(int batchProductId, int approvalDocumentId);

    List<BatchApprovalDocument> getAllByBatchProductId(int batchProductId);

    List<BatchApprovalDocument> getAllByDocumentId(int approvalDocumentId);

}
