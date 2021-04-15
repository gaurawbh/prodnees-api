package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.StageApproverDao;
import com.prodnees.domain.rels.StageApprover;
import com.prodnees.service.rels.StageApproverService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StageApproverServiceImpl implements StageApproverService {
    private final StageApproverDao stageApproverDao;

    public StageApproverServiceImpl(StageApproverDao stageApproverDao) {
        this.stageApproverDao = stageApproverDao;
    }

    @Override
    public StageApprover save(StageApprover stageApprover) {
        return stageApproverDao.save(stageApprover);
    }

    @Override
    public Optional<StageApprover> findByStageIdAndApproverId(int stageId, int approverId) {
        return stageApproverDao.findByStageIdAndApproverId(stageId, approverId);
    }

    @Override
    public List<StageApprover> getAllByStageId(int stageId) {
        return stageApproverDao.getAllByStageId(stageId);
    }

    @Override
    public List<StageApprover> getAllByApproverId(int approverId) {
        return stageApproverDao.getAllByApproverId(approverId);
    }
}
