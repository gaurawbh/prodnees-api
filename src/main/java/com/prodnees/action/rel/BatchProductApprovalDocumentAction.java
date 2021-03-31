package com.prodnees.action.rel;

import com.prodnees.domain.BatchProductApprovalDocument;
import com.prodnees.model.StateApprovalDocumentModel;
import java.util.List;
import java.util.Optional;

public interface BatchProductApprovalDocumentAction {
    StateApprovalDocumentModel save(BatchProductApprovalDocument batchProductApprovalDocument);

    Optional<BatchProductApprovalDocument> findByBatchProductIdAndApprovalDocumentId(int batchProductId, int approvalDocumentId);

    List<BatchProductApprovalDocument> getAllByBatchProductId(int batchProductId);

    List<BatchProductApprovalDocument> getAllByApprovalDocumentId(int approvalDocumentId);

}
