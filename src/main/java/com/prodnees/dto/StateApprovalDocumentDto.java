package com.prodnees.dto;

public class StateApprovalDocumentDto {
    private int stateId;
    private int documentId;
    private String approverEmail;

    public int getStateId() {
        return stateId;
    }

    public StateApprovalDocumentDto setStateId(int stateId) {
        this.stateId = stateId;
        return this;
    }

    public int getDocumentId() {
        return documentId;
    }

    public StateApprovalDocumentDto setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public String getApproverEmail() {
        return approverEmail;
    }

    public StateApprovalDocumentDto setApproverEmail(String approverEmail) {
        this.approverEmail = approverEmail;
        return this;
    }
}
