package com.prodnees.dao.rels;

import com.prodnees.domain.rels.StageApprover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StageApproverDao extends JpaRepository<StageApprover, Integer> {
    Optional<StageApprover> findByStageIdAndApproverId(int stageId, int approverId);

    List<StageApprover> getAllByStageId(int stageId);

    List<StageApprover> getAllByApproverId(int approverId);
}
