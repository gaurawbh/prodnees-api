package com.prodnees.model;

import com.prodnees.domain.rels.ObjectRightType;

public class ProductRightModel {

    private ProductModel productModel;
    private UserModel userModel;
    private ObjectRightType objectRightType;

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

    public ObjectRightType getObjectRightsType() {
        return objectRightType;
    }

    public ProductRightModel setObjectRightsType(ObjectRightType objectRightType) {
        this.objectRightType = objectRightType;
        return this;
    }
}
