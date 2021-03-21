package com.prodnees.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ApprovalDocument {

    @Id
    @GeneratedValue
    private int id;
    private int documentId;
    private ApprovalDocumentState state;
    private int approverId;

    public int getId() {
        return id;
    }

    public ApprovalDocument setId(int id) {
        this.id = id;
        return this;
    }

    public int getDocumentId() {
        return documentId;
    }

    public ApprovalDocument setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public ApprovalDocumentState getState() {
        return state;
    }

    public ApprovalDocument setState(ApprovalDocumentState approvalDocumentState) {
        this.state = approvalDocumentState;
        return this;
    }

    public int getApproverId() {
        return approverId;
    }

    public ApprovalDocument setApproverId(int approverId) {
        this.approverId = approverId;
        return this;
    }
}
