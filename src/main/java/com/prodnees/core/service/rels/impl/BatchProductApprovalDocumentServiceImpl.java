package com.prodnees.core.service.rels.impl;

import com.prodnees.core.dao.rels.BatchApprovalDocumentDao;
import com.prodnees.core.domain.batch.BatchApprovalDocument;
import com.prodnees.core.service.rels.BatchProductApprovalDocumentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatchProductApprovalDocumentServiceImpl implements BatchProductApprovalDocumentService {
    private final BatchApprovalDocumentDao batchApprovalDocumentDao;

    public BatchProductApprovalDocumentServiceImpl(BatchApprovalDocumentDao batchApprovalDocumentDao) {
        this.batchApprovalDocumentDao = batchApprovalDocumentDao;
    }

    @Override
    public BatchApprovalDocument save(BatchApprovalDocument batchApprovalDocument) {
        return batchApprovalDocumentDao.save(batchApprovalDocument);
    }

    @Override
    public Optional<BatchApprovalDocument> findByBatchIdAndApprovalDocumentId(int batchId, int approvalDocumentId) {
        return batchApprovalDocumentDao.findByBatchIdAndDocumentId(batchId, approvalDocumentId);
    }

    @Override
    public List<BatchApprovalDocument> getAllByBatchId(int batchId) {
        return batchApprovalDocumentDao.getAllByBatchId(batchId);
    }

    @Override
    public List<BatchApprovalDocument> getAllByApprovalDocumentId(int approvalDocumentId) {
        return batchApprovalDocumentDao.getAllByDocumentId(approvalDocumentId);
    }
}
