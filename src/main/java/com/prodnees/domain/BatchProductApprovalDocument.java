package com.prodnees.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BatchProductApprovalDocument {
    @Id
    @GeneratedValue
    private int id;
    private int batchProductId;
    private int documentId;
    private ApprovalDocumentState state;
    private int approverId;
    private String approverEmail;

    public String getApproverEmail() {
        return approverEmail;
    }

    public BatchProductApprovalDocument setApproverEmail(String approverEmail) {
        this.approverEmail = approverEmail;
        return this;
    }

    public int getId() {
        return id;
    }

    public BatchProductApprovalDocument setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchProductId() {
        return batchProductId;
    }

    public BatchProductApprovalDocument setBatchProductId(int eventId) {
        this.batchProductId = eventId;
        return this;
    }

    public int getDocumentId() {
        return documentId;
    }

    public BatchProductApprovalDocument setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public ApprovalDocumentState getState() {
        return state;
    }

    public BatchProductApprovalDocument setState(ApprovalDocumentState state) {
        this.state = state;
        return this;
    }

    public int getApproverId() {
        return approverId;
    }

    public BatchProductApprovalDocument setApproverId(int approverId) {
        this.approverId = approverId;
        return this;
    }
}
