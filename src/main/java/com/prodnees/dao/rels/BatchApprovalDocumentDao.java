package com.prodnees.dao.rels;

import com.prodnees.domain.batch.BatchApprovalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BatchApprovalDocumentDao extends JpaRepository<BatchApprovalDocument, Integer> {
    Optional<BatchApprovalDocument> findByBatchIdAndDocumentId(int batchId, int approvalDocumentId);

    List<BatchApprovalDocument> getAllByBatchId(int batchId);

    List<BatchApprovalDocument> getAllByDocumentId(int approvalDocumentId);

}
