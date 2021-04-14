package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.BatchProductApprovalDocumentDao;
import com.prodnees.domain.batch.BatchApprovalDocument;
import com.prodnees.service.rels.BatchProductApprovalDocumentService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BatchProductApprovalDocumentServiceImpl implements BatchProductApprovalDocumentService {
    private final BatchProductApprovalDocumentDao batchProductApprovalDocumentDao;

    public BatchProductApprovalDocumentServiceImpl(BatchProductApprovalDocumentDao batchProductApprovalDocumentDao) {
        this.batchProductApprovalDocumentDao = batchProductApprovalDocumentDao;
    }

    @Override
    public BatchApprovalDocument save(BatchApprovalDocument batchApprovalDocument) {
        return batchProductApprovalDocumentDao.save(batchApprovalDocument);
    }

    @Override
    public Optional<BatchApprovalDocument> findByBatchProductIdAndApprovalDocumentId(int batchProductId, int approvalDocumentId) {
        return batchProductApprovalDocumentDao.findByBatchProductIdAndDocumentId(batchProductId, approvalDocumentId);
    }

    @Override
    public List<BatchApprovalDocument> getAllByBatchProductId(int batchProductId) {
        return batchProductApprovalDocumentDao.getAllByBatchProductId(batchProductId);
    }

    @Override
    public List<BatchApprovalDocument> getAllByApprovalDocumentId(int approvalDocumentId) {
        return batchProductApprovalDocumentDao.getAllByDocumentId(approvalDocumentId);
    }
}
