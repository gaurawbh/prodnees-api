package com.prodnees.service.rels;

import com.prodnees.domain.rels.StateApprover;
import java.util.List;
import java.util.Optional;

public interface StateApproverService {

    StateApprover save(StateApprover stateApprover);

   Optional< StateApprover> findByStateIdAndApproverId(int stateId, int approverId);

    List<StateApprover> getAllByStateId(int stateId);

    List<StateApprover> getAllByApproverId(int approverId);

}
