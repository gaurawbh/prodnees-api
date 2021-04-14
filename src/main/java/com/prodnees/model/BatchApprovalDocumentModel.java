package com.prodnees.model;

public class BatchApprovalDocumentModel {
    private int batchProductId;
    private StateApprovalDocumentModel stateApprovalDocumentModel;

    public StateApprovalDocumentModel getApprovalDocumentModel() {
        return stateApprovalDocumentModel;
    }

    public BatchApprovalDocumentModel setApprovalDocumentModel(StateApprovalDocumentModel stateApprovalDocumentModel) {
        this.stateApprovalDocumentModel = stateApprovalDocumentModel;
        return this;
    }

    public int getBatchProductId() {
        return batchProductId;
    }

    public BatchApprovalDocumentModel setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
        return this;
    }
}
