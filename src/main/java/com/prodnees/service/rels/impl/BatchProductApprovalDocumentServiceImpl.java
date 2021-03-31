package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.BatchProductApprovalDocumentDao;
import com.prodnees.domain.BatchProductApprovalDocument;
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
    public BatchProductApprovalDocument save(BatchProductApprovalDocument batchProductApprovalDocument) {
        return batchProductApprovalDocumentDao.save(batchProductApprovalDocument);
    }

    @Override
    public Optional<BatchProductApprovalDocument> findByBatchProductIdAndApprovalDocumentId(int batchProductId, int approvalDocumentId) {
        return batchProductApprovalDocumentDao.findByBatchProductIdAndDocumentId(batchProductId, approvalDocumentId);
    }

    @Override
    public List<BatchProductApprovalDocument> getAllByBatchProductId(int batchProductId) {
        return batchProductApprovalDocumentDao.getAllByBatchProductId(batchProductId);
    }

    @Override
    public List<BatchProductApprovalDocument> getAllByApprovalDocumentId(int approvalDocumentId) {
        return batchProductApprovalDocumentDao.getAllByDocumentId(approvalDocumentId);
    }
}
