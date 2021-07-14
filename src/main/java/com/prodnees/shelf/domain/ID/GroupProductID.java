package com.prodnees.shelf.domain.ID;

import java.io.Serializable;

public class GroupProductID implements Serializable {

    private int productId;

    private int productGroupId;

    public GroupProductID(int productId, int productGroupId) {
        this.productId = productId;
        this.productGroupId = productGroupId;
    }

    public GroupProductID() {
    }

    public int getProductId() {
        return productId;
    }

    public GroupProductID setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public int getProductGroupId() {
        return productGroupId;
    }

    public GroupProductID setProductGroupId(int productGroupId) {
        this.productGroupId = productGroupId;
        return this;
    }
}

