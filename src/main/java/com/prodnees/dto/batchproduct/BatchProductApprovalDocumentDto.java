package com.prodnees.dto.batchproduct;

import com.prodnees.config.constants.LocalConstants;
import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;

public class BatchProductApprovalDocumentDto {
    @Positive(message = "batchProductId must be a positive number")
    private int batchProductId;
    @Positive(message = "documentId must be a positive number")
    private int documentId;
    @Email(regexp = LocalConstants.EMAIL_REGEX, message = "invalid email format for approverEmail")
    private String approverEmail;

    public int getBatchProductId() {
        return batchProductId;
    }

    public BatchProductApprovalDocumentDto setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
        return this;
    }

    public int getDocumentId() {
        return documentId;
    }

    public BatchProductApprovalDocumentDto setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public String getApproverEmail() {
        return approverEmail;
    }

    public BatchProductApprovalDocumentDto setApproverEmail(String approverEmail) {
        this.approverEmail = approverEmail;
        return this;
    }
}
