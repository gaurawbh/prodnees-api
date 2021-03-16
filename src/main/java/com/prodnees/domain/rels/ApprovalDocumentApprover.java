package com.prodnees.domain.rels;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(ApprovalDocumentApprover.class)
public class ApprovalDocumentApprover implements Serializable {
    @Id
    private int approvalDocumentId;
    @Id
    private int approverId;

    public int getApprovalDocumentId() {
        return approvalDocumentId;
    }

    public ApprovalDocumentApprover setApprovalDocumentId(int approvalDocumentId) {
        this.approvalDocumentId = approvalDocumentId;
        return this;
    }

    public int getApproverId() {
        return approverId;
    }

    public ApprovalDocumentApprover setApproverId(int userId) {
        this.approverId = userId;
        return this;
    }
}
