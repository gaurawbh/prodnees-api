package com.prodnees.action.rel;

import com.prodnees.domain.batch.BatchApprovalDocument;
import com.prodnees.model.stage.StageApprovalDocumentModel;
import java.util.List;
import java.util.Optional;

public interface BatchProductApprovalDocumentAction {
    StageApprovalDocumentModel save(BatchApprovalDocument batchApprovalDocument);

    Optional<BatchApprovalDocument> findByBatchIdAndApprovalDocumentId(int batchId, int approvalDocumentId);

    List<BatchApprovalDocument> getAllByBatchId(int batchId);

    List<BatchApprovalDocument> getAllByApprovalDocumentId(int approvalDocumentId);

}
