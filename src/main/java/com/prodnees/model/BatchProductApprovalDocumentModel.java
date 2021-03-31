package com.prodnees.model;

public class BatchProductApprovalDocumentModel {
    private int batchProductId;
    private StateApprovalDocumentModel stateApprovalDocumentModel;

    public StateApprovalDocumentModel getApprovalDocumentModel() {
        return stateApprovalDocumentModel;
    }

    public BatchProductApprovalDocumentModel setApprovalDocumentModel(StateApprovalDocumentModel stateApprovalDocumentModel) {
        this.stateApprovalDocumentModel = stateApprovalDocumentModel;
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
