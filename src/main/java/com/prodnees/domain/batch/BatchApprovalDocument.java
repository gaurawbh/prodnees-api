package com.prodnees.domain.batch;

import com.prodnees.domain.enums.ApprovalDocumentState;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BatchApprovalDocument {
    @Id
    @GeneratedValue
    private int id;
    private int batchId;
    private int documentId;
    private ApprovalDocumentState state;
    private int approverId;
    private String approverEmail;

    public String getApproverEmail() {
        return approverEmail;
    }

    public BatchApprovalDocument setApproverEmail(String approverEmail) {
        this.approverEmail = approverEmail;
        return this;
    }

    public int getId() {
        return id;
    }

    public BatchApprovalDocument setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchId() {
        return batchId;
    }

    public BatchApprovalDocument setBatchId(int batchId) {
        this.batchId = batchId;
        return this;
    }

    public int getDocumentId() {
        return documentId;
    }

    public BatchApprovalDocument setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public ApprovalDocumentState getState() {
        return state;
    }

    public BatchApprovalDocument setState(ApprovalDocumentState state) {
        this.state = state;
        return this;
    }

    public int getApproverId() {
        return approverId;
    }

    public BatchApprovalDocument setApproverId(int approverId) {
        this.approverId = approverId;
        return this;
    }
}
