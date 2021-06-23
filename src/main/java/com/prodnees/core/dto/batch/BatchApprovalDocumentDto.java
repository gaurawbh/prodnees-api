package com.prodnees.core.dto.batch;

import com.prodnees.core.config.constants.LocalConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;

public class BatchApprovalDocumentDto {
    @Positive(message = "batchProductId must be a positive number")
    private int batchId;
    @Positive(message = "documentId must be a positive number")
    private int documentId;
    @Email(regexp = LocalConstants.EMAIL_REGEX, message = "invalid email format for approverEmail")
    private String approverEmail;

    public int getBatchId() {
        return batchId;
    }

    public BatchApprovalDocumentDto setBatchId(int batchId) {
        this.batchId = batchId;
        return this;
    }

    public int getDocumentId() {
        return documentId;
    }

    public BatchApprovalDocumentDto setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public String getApproverEmail() {
        return approverEmail;
    }

    public BatchApprovalDocumentDto setApproverEmail(String approverEmail) {
        this.approverEmail = approverEmail;
        return this;
    }
}
