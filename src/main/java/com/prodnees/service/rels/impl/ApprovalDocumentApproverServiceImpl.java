package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.ApprovalDocumentApproverDao;
import com.prodnees.domain.rels.ApprovalDocumentApprover;
import com.prodnees.service.rels.ApprovalDocumentApproverService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApprovalDocumentApproverServiceImpl implements ApprovalDocumentApproverService {

    private final ApprovalDocumentApproverDao approvalDocumentApproverDao;

    public ApprovalDocumentApproverServiceImpl(ApprovalDocumentApproverDao approvalDocumentApproverDao) {
        this.approvalDocumentApproverDao = approvalDocumentApproverDao;
    }

    @Override
    public ApprovalDocumentApprover save(ApprovalDocumentApprover approvalDocumentApprover) {
        return approvalDocumentApproverDao.save(approvalDocumentApprover);
    }

    @Override
    public ApprovalDocumentApprover getByApprovalDocumentIdAndApproverId(int approvalDocumentId, int approverId) {
        return null;
    }

    @Override
    public List<ApprovalDocumentApprover> getAllByApprovalDocumentId(int approvalDocumentId) {
        return null;
    }

    @Override
    public List<ApprovalDocumentApprover> getAllByApproverId(int approvalDocumentId) {
        return null;
    }
}
