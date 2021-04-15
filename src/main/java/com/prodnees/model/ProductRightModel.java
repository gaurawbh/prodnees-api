package com.prodnees.model;

import com.prodnees.domain.enums.ObjectRight;
import com.prodnees.model.user.UserModel;

public class ProductRightModel {

    private ProductModel productModel;
    private UserModel userModel;
    private ObjectRight objectRight;

    public ProductModel getProductModel() {
        return productModel;
    }

    public ProductRightModel setProductModel(ProductModel productModel) {
        this.productModel = productModel;
        return this;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public ProductRightModel setUserModel(UserModel userModel) {
        this.userModel = userModel;
        return this;
    }

    public ObjectRight getObjectRightsType() {
        return objectRight;
    }

    public ProductRightModel setObjectRightsType(ObjectRight objectRight) {
        this.objectRight = objectRight;
        return this;
    }
}
