package com.prodnees.service.rels;

import com.prodnees.domain.rels.StateApprover;

import java.util.List;

public interface StateApproverService {

    StateApprover save(StateApprover stateApprover);

    StateApprover getByStateIdAndApproverId(int stateId, int approverId);

    List<StateApprover> getAllByStateId(int stateId);

    List<StateApprover> getAllByApproverId(int approverId);

}
