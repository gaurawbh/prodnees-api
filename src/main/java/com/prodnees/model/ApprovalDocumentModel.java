package com.prodnees.model;

import com.prodnees.domain.ApprovalDocumentState;

/**
 * Document that needs to be approved
 * Used in States where a State may not be complete until a Document has been approved
 */
public class ApprovalDocumentModel {
    private int id;
    private int approverId;
    private String name;
    private String documentUrl;
    private String documentDownloadUrl;
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

    public String getName() {
        return name;
    }

    public ApprovalDocumentModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDocumentDownloadUrl() {
        return documentDownloadUrl;
    }

    public ApprovalDocumentModel setDocumentDownloadUrl(String documentDownloadUrl) {
        this.documentDownloadUrl = documentDownloadUrl;
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
