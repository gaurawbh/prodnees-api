package com.prodnees.service.rels;

import com.prodnees.domain.BatchProductApprovalDocument;
import java.util.List;
import java.util.Optional;

public interface BatchProductApprovalDocumentService {

    BatchProductApprovalDocument save(BatchProductApprovalDocument batchProductApprovalDocument);

    Optional<BatchProductApprovalDocument> findByBatchProductIdAndApprovalDocumentId(int batchProductId, int approvalDocumentId);

    List<BatchProductApprovalDocument> getAllByBatchProductId(int batchProductId);

    List<BatchProductApprovalDocument> getAllByApprovalDocumentId(int approvalDocumentId);

}
