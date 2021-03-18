package com.prodnees.domain.rels.id;

import java.io.Serializable;

public class ProductRightsId implements Serializable {
    private int productId;
    private int userId;

    public int getProductId() {
        return productId;
    }

    public ProductRightsId setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public ProductRightsId setUserId(int userId) {
        this.userId = userId;
        return this;
    }
}
