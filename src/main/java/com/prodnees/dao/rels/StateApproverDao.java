package com.prodnees.dao.rels;

import com.prodnees.domain.rels.StateApprover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StateApproverDao extends JpaRepository<StateApprover, Integer> {
    Optional<StateApprover> findByStateIdAndApproverId(int stateId, int approverId);

    List<StateApprover> getAllByStateId(int stateId);

    List<StateApprover> getAllByApproverId(int approverId);
}
