package com.prodnees.core.domain.rels.id;

import java.io.Serializable;

public class ProductRightId implements Serializable {
    private int productId;
    private int userId;

    public int getProductId() {
        return productId;
    }

    public ProductRightId setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public ProductRightId setUserId(int userId) {
        this.userId = userId;
        return this;
    }
}
