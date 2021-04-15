package com.prodnees.domain.rels.id;

import java.io.Serializable;


public class BatchRightId implements Serializable {
    private int batchProductId;
    private int userId;

    public BatchRightId() {
    }

    public BatchRightId(int batchProductId, int userId) {
        this.batchProductId = batchProductId;
        this.userId = userId;
    }

    public int getBatchId() {
        return batchProductId;
    }

    public BatchRightId setBatchId(int batchProductId) {
        this.batchProductId = batchProductId;
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
