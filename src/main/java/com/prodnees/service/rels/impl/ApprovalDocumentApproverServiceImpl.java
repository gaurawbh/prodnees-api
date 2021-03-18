package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.ApprovalDocumentApproverDao;
import com.prodnees.domain.rels.ApprovalDocumentApprover;
import com.prodnees.service.rels.ApprovalDocumentApproverService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<ApprovalDocumentApprover> getByApprovalDocumentIdAndApproverId(int approvalDocumentId, int approverId) {
        return approvalDocumentApproverDao.findByApprovalDocumentIdAndApproverId(approvalDocumentId, approverId);
    }

    @Override
    public List<ApprovalDocumentApprover> getAllByApprovalDocumentId(int approvalDocumentId) {
        return approvalDocumentApproverDao.getAllByApprovalDocumentId(approvalDocumentId);
    }

    @Override
    public List<ApprovalDocumentApprover> getAllByApproverId(int approverId) {
        return approvalDocumentApproverDao.getAllByApproverId(approverId);
    }
}
