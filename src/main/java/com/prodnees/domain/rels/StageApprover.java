package com.prodnees.domain.rels;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(StageApprover.class)
public class StageApprover implements Serializable {
    @Id
    private int stageId;
    @Id
    private int approverId;


    public int getStageId() {
        return stageId;
    }

    public StageApprover setStageId(int stateId) {
        this.stageId = stateId;
        return this;
    }

    public int getApproverId() {
        return approverId;
    }

    public StageApprover setApproverId(int approverId) {
        this.approverId = approverId;
        return this;
    }
}
