package com.prodnees.dao.rels;

import com.prodnees.domain.rels.ApprovalDocumentApprover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApprovalDocumentApproverDao extends JpaRepository<ApprovalDocumentApprover, Integer> {

    List<ApprovalDocumentApprover> getAllByApprovalDocumentId(int approvalDocumentId);

    List<ApprovalDocumentApprover> getAllByApproverId(int approverId);

    Optional<ApprovalDocumentApprover> findByApprovalDocumentIdAndApproverId(int approvalDocumentId, int approverId);

}
