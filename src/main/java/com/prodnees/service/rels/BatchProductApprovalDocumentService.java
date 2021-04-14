package com.prodnees.service.rels;

import com.prodnees.domain.batch.BatchApprovalDocument;
import java.util.List;
import java.util.Optional;

public interface BatchProductApprovalDocumentService {

    BatchApprovalDocument save(BatchApprovalDocument batchApprovalDocument);

    Optional<BatchApprovalDocument> findByBatchProductIdAndApprovalDocumentId(int batchProductId, int approvalDocumentId);

    List<BatchApprovalDocument> getAllByBatchProductId(int batchProductId);

    List<BatchApprovalDocument> getAllByApprovalDocumentId(int approvalDocumentId);

}
