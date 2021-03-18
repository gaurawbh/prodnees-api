package com.prodnees.service.rels;

import com.prodnees.domain.rels.ApprovalDocumentApprover;

import java.util.List;
import java.util.Optional;

public interface ApprovalDocumentApproverService {

    ApprovalDocumentApprover save(ApprovalDocumentApprover approvalDocumentApprover);

    Optional<ApprovalDocumentApprover> getByApprovalDocumentIdAndApproverId(int approvalDocumentId, int approverId);

    List<ApprovalDocumentApprover> getAllByApprovalDocumentId(int approvalDocumentId);

    List<ApprovalDocumentApprover> getAllByApproverId(int approverId);


}
