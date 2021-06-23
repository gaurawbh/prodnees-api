package com.prodnees.core.dto.stage;

public class StageApprovalDocumentDto {
    private int stageId;
    private int documentId;
    private String approverEmail;

    public int getStageId() {
        return stageId;
    }

    public StageApprovalDocumentDto setStageId(int stageId) {
        this.stageId = stageId;
        return this;
    }

    public int getDocumentId() {
        return documentId;
    }

    public StageApprovalDocumentDto setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public String getApproverEmail() {
        return approverEmail;
    }

    public StageApprovalDocumentDto setApproverEmail(String approverEmail) {
        this.approverEmail = approverEmail;
        return this;
    }
}
