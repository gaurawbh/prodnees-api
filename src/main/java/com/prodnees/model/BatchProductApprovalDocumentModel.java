package com.prodnees.model;

public class BatchProductApprovalDocumentModel {
    private int batchProductId;
    private ApprovalDocumentModel approvalDocumentModel;

    public ApprovalDocumentModel getApprovalDocumentModel() {
        return approvalDocumentModel;
    }

    public BatchProductApprovalDocumentModel setApprovalDocumentModel(ApprovalDocumentModel approvalDocumentModel) {
        this.approvalDocumentModel = approvalDocumentModel;
        return this;
    }

    public int getBatchProductId() {
        return batchProductId;
    }

    public BatchProductApprovalDocumentModel setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
        return this;
    }
}
