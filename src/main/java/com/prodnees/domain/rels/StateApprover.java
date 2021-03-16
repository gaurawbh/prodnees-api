package com.prodnees.domain.rels;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(StateApprover.class)
public class StateApprover implements Serializable {
    @Id
    private int stateId;
    @Id
    private int approverId;


    public int getStateId() {
        return stateId;
    }

    public StateApprover setStateId(int stateId) {
        this.stateId = stateId;
        return this;
    }

    public int getApproverId() {
        return approverId;
    }

    public StateApprover setApproverId(int approverId) {
        this.approverId = approverId;
        return this;
    }
}
