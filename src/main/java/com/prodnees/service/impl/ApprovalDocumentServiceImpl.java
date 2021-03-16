package com.prodnees.service.impl;

import com.prodnees.dao.ApprovalDocumentDao;
import com.prodnees.domain.ApprovalDocument;
import com.prodnees.domain.DocumentState;
import com.prodnees.service.ApprovalDocumentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ApprovalDocumentServiceImpl implements ApprovalDocumentService {
    private final ApprovalDocumentDao approvalDocumentDao;

    public ApprovalDocumentServiceImpl(ApprovalDocumentDao approvalDocumentDao) {
        this.approvalDocumentDao = approvalDocumentDao;
    }

    @Override
    public ApprovalDocument save(ApprovalDocument approvalDocument) {
        return approvalDocumentDao.save(approvalDocument);
    }

    @Override
    public ApprovalDocument getById(int id) {
        return null;
    }

    @Override
    public List<ApprovalDocument> getAllByBatchProductId(int batchProductId) {
        return null;
    }

    @Override
    public List<ApprovalDocument> getAllByStateId(int stateId) {
        return null;
    }

    @Override
    public List<ApprovalDocument> getAllByStateIdAndDocumentState(int stateId, DocumentState documentState) {
        return null;
    }

    @Override
    public List<ApprovalDocument> getAllByBatchProductIdAndDocumentState(int stateId, DocumentState documentState) {
        return null;
    }
}
