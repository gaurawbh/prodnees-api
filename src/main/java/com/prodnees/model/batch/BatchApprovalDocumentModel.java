package com.prodnees.model.batch;

import com.prodnees.model.state.StageApprovalDocumentModel;

public class BatchApprovalDocumentModel {
    private int batchId;
    private StageApprovalDocumentModel stageApprovalDocumentModel;

    public StageApprovalDocumentModel getApprovalDocumentModel() {
        return stageApprovalDocumentModel;
    }

    public BatchApprovalDocumentModel setApprovalDocumentModel(StageApprovalDocumentModel stageApprovalDocumentModel) {
        this.stageApprovalDocumentModel = stageApprovalDocumentModel;
        return this;
    }

    public int getBatchId() {
        return batchId;
    }

    public BatchApprovalDocumentModel setBatchId(int batchId) {
        this.batchId = batchId;
        return this;
    }
}
