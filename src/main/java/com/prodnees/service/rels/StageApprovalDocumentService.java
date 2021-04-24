package com.prodnees.service.rels;

import com.prodnees.domain.stage.StageApprovalDocument;

import java.util.List;
import java.util.Optional;

public interface StageApprovalDocumentService {
    StageApprovalDocument save(StageApprovalDocument stageApprovalDocument);

    Optional<StageApprovalDocument> findByStageIdAndApprovalDocumentId(int stageId, int approvalDocumentId);

    List<StageApprovalDocument> getAllByStageId(int stageId);

    List<StageApprovalDocument> getAllByApprovalDocumentId(int approvalDocumentId);

}
