package com.prodnees.model;

import com.prodnees.domain.batch.Product;
import com.prodnees.domain.enums.ObjectRight;
import com.prodnees.model.user.UserModel;

public class ProductRightModel {

    private Product product;
    private UserModel userModel;
    private ObjectRight objectRight;



    public UserModel getUserModel() {
        return userModel;
    }

    public ProductRightModel setUserModel(UserModel userModel) {
        this.userModel = userModel;
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
