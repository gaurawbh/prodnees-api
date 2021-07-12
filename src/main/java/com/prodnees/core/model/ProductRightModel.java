package com.prodnees.core.model;

import com.prodnees.core.domain.batch.Product;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.model.user.NeesUserDetails;

public class ProductRightModel {

    private Product product;
    private NeesUserDetails neesUserDetails;
    private ObjectRight objectRight;

    public NeesUserDetails getUserModel() {
        return neesUserDetails;
    }

    public ProductRightModel setUserModel(NeesUserDetails neesUserDetails) {
        this.neesUserDetails = neesUserDetails;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public ProductRightModel setProduct(Product product) {
        this.product = product;
        return this;
    }

    public ObjectRight getObjectRight() {
        return objectRight;
    }

    public ProductRightModel setObjectRight(ObjectRight objectRight) {
        this.objectRight = objectRight;
        return this;
    }
}
