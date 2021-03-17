package com.prodnees.service.impl;

import com.prodnees.dao.ApprovalDocumentDao;
import com.prodnees.domain.ApprovalDocument;
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
        return approvalDocumentDao.getById(id);
    }

    @Override
    public List<ApprovalDocument> getAllByDocumentId(int documentId) {
        return approvalDocumentDao.getAllByDocumentId(documentId);
    }
}
