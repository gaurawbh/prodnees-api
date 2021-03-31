package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.StateApprovalDocumentDao;
import com.prodnees.domain.StateApprovalDocument;
import com.prodnees.service.rels.StateApprovalDocumentService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class StateApprovalDocumentServiceImpl implements StateApprovalDocumentService {
    private final StateApprovalDocumentDao stateApprovalDocumentDao;

    public StateApprovalDocumentServiceImpl(StateApprovalDocumentDao stateApprovalDocumentDao) {
        this.stateApprovalDocumentDao = stateApprovalDocumentDao;
    }

    @Override
    public StateApprovalDocument save(StateApprovalDocument stateApprovalDocument) {
        return stateApprovalDocumentDao.save(stateApprovalDocument);
    }

    @Override
    public Optional<StateApprovalDocument> findByStateIdAndApprovalDocumentId(int stateId, int approvalDocumentId) {
        return stateApprovalDocumentDao.findByStateIdAndDocumentId(stateId, approvalDocumentId);
    }

    @Override
    public List<StateApprovalDocument> getAllByStateId(int stateId) {
        return stateApprovalDocumentDao.getAllByStateId(stateId);
    }

    @Override
    public List<StateApprovalDocument> getAllByApprovalDocumentId(int approvalDocumentId) {
        return stateApprovalDocumentDao.getAllByDocumentId(approvalDocumentId);
    }
}
