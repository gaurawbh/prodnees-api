package com.prodnees.service.rels;

import com.prodnees.domain.rels.StateApprovalDocument;

import java.util.List;
import java.util.Optional;

public interface StateApprovalDocumentService {
    StateApprovalDocument save(StateApprovalDocument stateApprovalDocument);

    Optional<StateApprovalDocument> findByStateIdAndApprovalDocumentId(int stateId, int approvalDocumentId);

    List<StateApprovalDocument> getAllByStateId(int stateId);

    List<StateApprovalDocument> getAllByApprovalDocumentId(int approvalDocumentId);

}
