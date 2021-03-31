package com.prodnees.action.rel;

import com.prodnees.domain.rels.BatchProductApprovalDocument;
import com.prodnees.model.ApprovalDocumentModel;
import java.util.List;
import java.util.Optional;

public interface BatchProductApprovalDocumentAction {
    ApprovalDocumentModel save(BatchProductApprovalDocument batchProductApprovalDocument);

    Optional<BatchProductApprovalDocument> findByBatchProductIdAndApprovalDocumentId(int batchProductId, int approvalDocumentId);

    List<BatchProductApprovalDocument> getAllByBatchProductId(int batchProductId);

    List<BatchProductApprovalDocument> getAllByApprovalDocumentId(int approvalDocumentId);

}
