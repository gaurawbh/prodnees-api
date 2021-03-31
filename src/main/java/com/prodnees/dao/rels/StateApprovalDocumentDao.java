package com.prodnees.dao.rels;

import com.prodnees.domain.StateApprovalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StateApprovalDocumentDao extends JpaRepository<StateApprovalDocument, Integer> {
    Optional<StateApprovalDocument> findByStateIdAndDocumentId(int stateId, int approvalDocumentId);

    List<StateApprovalDocument> getAllByStateId(int stateId);

    List<StateApprovalDocument> getAllByDocumentId(int approvalDocumentId);

}
