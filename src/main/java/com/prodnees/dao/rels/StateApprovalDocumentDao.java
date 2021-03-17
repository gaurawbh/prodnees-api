package com.prodnees.dao.rels;

import com.prodnees.domain.rels.StateApprovalDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface StateApprovalDocumentDao extends JpaRepository<StateApprovalDocument, Integer> {
    Optional<StateApprovalDocument> findByStateIdAndApprovalDocumentId(int stateId, int approvalDocumentId);

    List<StateApprovalDocument> getAllByStateId(int stateId);

    List<StateApprovalDocument> getAllByApprovalDocumentId(int approvalDocumentId);

}
