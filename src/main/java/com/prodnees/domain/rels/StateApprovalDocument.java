package com.prodnees.domain.rels;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(StateApprovalDocument.class)
public class StateApprovalDocument implements Serializable {
    @Id
    private int stateId;
    @Id
    private int approvalDocumentId;

    public int getStateId() {
        return stateId;
    }

    public StateApprovalDocument setStateId(int stateId) {
        this.stateId = stateId;
        return this;
    }

    public int getApprovalDocumentId() {
        return approvalDocumentId;
    }

    public StateApprovalDocument setApprovalDocumentId(int approvalDocumentId) {
        this.approvalDocumentId = approvalDocumentId;
        return this;
    }
}
