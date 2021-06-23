package com.prodnees.core.service.rels.impl;

import com.prodnees.core.dao.rels.StageApprovalDocumentDao;
import com.prodnees.core.domain.stage.StageApprovalDocument;
import com.prodnees.core.service.rels.StageApprovalDocumentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StageApprovalDocumentServiceImpl implements StageApprovalDocumentService {
    private final StageApprovalDocumentDao stageApprovalDocumentDao;

    public StageApprovalDocumentServiceImpl(StageApprovalDocumentDao stageApprovalDocumentDao) {
        this.stageApprovalDocumentDao = stageApprovalDocumentDao;
    }

    @Override
    public StageApprovalDocument save(StageApprovalDocument stageApprovalDocument) {
        return stageApprovalDocumentDao.save(stageApprovalDocument);
    }

    @Override
    public Optional<StageApprovalDocument> findByStageIdAndApprovalDocumentId(int stageId, int approvalDocumentId) {
        return stageApprovalDocumentDao.findByStageIdAndDocumentId(stageId, approvalDocumentId);
    }

    @Override
    public List<StageApprovalDocument> getAllByStageId(int stageId) {
        return stageApprovalDocumentDao.getAllByStageId(stageId);
    }

    @Override
    public List<StageApprovalDocument> getAllByApprovalDocumentId(int approvalDocumentId) {
        return stageApprovalDocumentDao.getAllByDocumentId(approvalDocumentId);
    }
}
