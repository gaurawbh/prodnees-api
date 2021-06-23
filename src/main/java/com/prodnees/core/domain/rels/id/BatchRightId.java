package com.prodnees.core.domain.rels.id;

import java.io.Serializable;


public class BatchRightId implements Serializable {
    private int batchId;
    private int userId;

    public BatchRightId() {
    }

    public BatchRightId(int batchId, int userId) {
        this.batchId = batchId;
        this.userId = userId;
    }

    public int getBatchId() {
        return batchId;
    }

    public BatchRightId setBatchId(int batchId) {
        this.batchId = batchId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public BatchRightId setUserId(int userId) {
        this.userId = userId;
        return this;
    }
}
