package com.prodnees.dao.rels;

import com.prodnees.domain.rels.BatchProductApprovalDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BatchProductApprovalDocumentDao extends JpaRepository<BatchProductApprovalDocument, Integer> {
    Optional<BatchProductApprovalDocument> findByBatchProductIdAndApprovalDocumentId(int batchProductId, int approvalDocumentId);

    List<BatchProductApprovalDocument> getAllByBatchProductId(int batchProductId);

    List<BatchProductApprovalDocument> getAllByApprovalDocumentId(int approvalDocumentId);

}
