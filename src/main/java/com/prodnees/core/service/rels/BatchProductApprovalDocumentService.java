package com.prodnees.core.service.rels;

import com.prodnees.core.domain.batch.BatchApprovalDocument;

import java.util.List;
import java.util.Optional;

public interface BatchProductApprovalDocumentService {

    BatchApprovalDocument save(BatchApprovalDocument batchApprovalDocument);

    Optional<BatchApprovalDocument> findByBatchIdAndApprovalDocumentId(int batchId, int approvalDocumentId);

    List<BatchApprovalDocument> getAllByBatchId(int batchId);

    List<BatchApprovalDocument> getAllByApprovalDocumentId(int approvalDocumentId);

}
