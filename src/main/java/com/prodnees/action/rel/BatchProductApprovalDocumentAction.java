package com.prodnees.action.rel;

import com.prodnees.domain.batch.BatchApprovalDocument;
import com.prodnees.model.state.StateApprovalDocumentModel;
import java.util.List;
import java.util.Optional;

public interface BatchProductApprovalDocumentAction {
    StateApprovalDocumentModel save(BatchApprovalDocument batchApprovalDocument);

    Optional<BatchApprovalDocument> findByBatchProductIdAndApprovalDocumentId(int batchProductId, int approvalDocumentId);

    List<BatchApprovalDocument> getAllByBatchProductId(int batchProductId);

    List<BatchApprovalDocument> getAllByApprovalDocumentId(int approvalDocumentId);

}
