package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.StateApproverDao;
import com.prodnees.domain.rels.StateApprover;
import com.prodnees.service.rels.StateApproverService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StateApproverServiceImpl implements StateApproverService {
    private final StateApproverDao stateApproverDao;

    public StateApproverServiceImpl(StateApproverDao stateApproverDao) {
        this.stateApproverDao = stateApproverDao;
    }

    @Override
    public StateApprover save(StateApprover stateApprover) {
        return stateApproverDao.save(stateApprover);
    }

    @Override
    public Optional<StateApprover> findByStateIdAndApproverId(int stateId, int approverId) {
        return stateApproverDao.findByStateIdAndApproverId(stateId, approverId);
    }

    @Override
    public List<StateApprover> getAllByStateId(int stateId) {
        return null;
    }

    @Override
    public List<StateApprover> getAllByApproverId(int approverId) {
        return null;
    }
}
