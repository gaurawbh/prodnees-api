package com.prodnees.domain.rels.id;

import java.io.Serializable;


public class BatchProductRightsId implements Serializable {
    private int batchProductId;
    private int userId;

    public BatchProductRightsId() {
    }

    public BatchProductRightsId(int batchProductId, int userId) {
        this.batchProductId = batchProductId;
        this.userId = userId;
    }

    public int getBatchProductId() {
        return batchProductId;
    }

    public BatchProductRightsId setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public BatchProductRightsId setUserId(int userId) {
        this.userId = userId;
        return this;
    }
}
