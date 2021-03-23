package com.prodnees.domain.rels.id;

import java.io.Serializable;


public class BatchProductRightId implements Serializable {
    private int batchProductId;
    private int userId;

    public BatchProductRightId() {
    }

    public BatchProductRightId(int batchProductId, int userId) {
        this.batchProductId = batchProductId;
        this.userId = userId;
    }

    public int getBatchProductId() {
        return batchProductId;
    }

    public BatchProductRightId setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public BatchProductRightId setUserId(int userId) {
        this.userId = userId;
        return this;
    }
}
