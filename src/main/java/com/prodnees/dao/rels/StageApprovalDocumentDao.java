package com.prodnees.dao.rels;

import com.prodnees.domain.state.StageApprovalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StageApprovalDocumentDao extends JpaRepository<StageApprovalDocument, Integer> {
    Optional<StageApprovalDocument> findByStageIdAndDocumentId(int stageId, int approvalDocumentId);

    List<StageApprovalDocument> getAllByStageId(int stageId);

    List<StageApprovalDocument> getAllByDocumentId(int approvalDocumentId);

}
