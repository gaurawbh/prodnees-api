package com.prodnees.model;

import com.prodnees.domain.ApprovalDocumentState;

/**
 * Document that needs to be approved
 * Used in States where a State may not be complete until a Document has been approved
 */
public class StateApprovalDocumentModel {
    private int id;
    private int approverId;
    private String approverEmail;
    private String name;
    private int documentId;
    private String documentUrl;
    private String documentDownloadUrl;
    private ApprovalDocumentState approvalDocumentState;

    public int getId() {
        return id;
    }

    public StateApprovalDocumentModel setId(int id) {
        this.id = id;
        return this;
    }

    public int getApproverId() {
        return approverId;
    }

    public StateApprovalDocumentModel setApproverId(int approverId) {
        this.approverId = approverId;
        return this;
    }

    public String getApproverEmail() {
        return approverEmail;
    }

    public StateApprovalDocumentModel setApproverEmail(String approverEmail) {
        this.approverEmail = approverEmail;
        return this;
    }

    public int getDocumentId() {
        return documentId;
    }

    public StateApprovalDocumentModel setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public String getName() {
        return name;
    }

    public StateApprovalDocumentModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDocumentDownloadUrl() {
        return documentDownloadUrl;
    }

    public StateApprovalDocumentModel setDocumentDownloadUrl(String documentDownloadUrl) {
        this.documentDownloadUrl = documentDownloadUrl;
        return this;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public StateApprovalDocumentModel setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
        return this;
    }

    public ApprovalDocumentState getApprovalDocumentState() {
        return approvalDocumentState;
    }

    public StateApprovalDocumentModel setApprovalDocumentState(ApprovalDocumentState approvalDocumentState) {
        this.approvalDocumentState = approvalDocumentState;
        return this;
    }
}
