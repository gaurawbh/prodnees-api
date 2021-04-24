package com.prodnees.service.rels;

import com.prodnees.domain.rels.StageApprover;

import java.util.List;
import java.util.Optional;

public interface StageApproverService {

    StageApprover save(StageApprover stageApprover);

    Optional<StageApprover> findByStageIdAndApproverId(int stageId, int approverId);

    List<StageApprover> getAllByStageId(int stageId);

    List<StageApprover> getAllByApproverId(int approverId);

}
