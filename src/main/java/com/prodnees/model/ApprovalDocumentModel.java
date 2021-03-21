package com.prodnees.model;

import com.prodnees.domain.ApprovalDocumentState;

/**
 * Document that needs to be approved
 * Used in States where a State may not be complete until a Document has been approved
 */
public class ApprovalDocumentModel {
    private int id;
    private int approverId;
    private DocumentModel document;
    private String documentUrl;
    private ApprovalDocumentState approvalDocumentState;

    public int getId() {
        return id;
    }

    public ApprovalDocumentModel setId(int id) {
        this.id = id;
        return this;
    }

    public int getApproverId() {
        return approverId;
    }

    public ApprovalDocumentModel setApproverId(int approverId) {
        this.approverId = approverId;
        return this;
    }

    public DocumentModel getDocument() {
        return document;
    }

    public ApprovalDocumentModel setDocument(DocumentModel document) {
        this.document = document;
        return this;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public ApprovalDocumentModel setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
        return this;
    }

    public ApprovalDocumentState getApprovalDocumentState() {
        return approvalDocumentState;
    }

    public ApprovalDocumentModel setApprovalDocumentState(ApprovalDocumentState approvalDocumentState) {
        this.approvalDocumentState = approvalDocumentState;
        return this;
    }
}
