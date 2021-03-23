package com.prodnees.model;

import com.prodnees.domain.BatchProduct;
import com.prodnees.domain.rels.ObjectRightType;

public class BatchProductRightModel {

    private BatchProduct batchProduct;
    private UserModel userModel;
    private ObjectRightType objectRightType;

    public BatchProduct getBatchProduct() {
        return batchProduct;
    }

    public BatchProductRightModel setBatchProduct(BatchProduct batchProduct) {
        this.batchProduct = batchProduct;
        return this;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public BatchProductRightModel setUserModel(UserModel userModel) {
        this.userModel = userModel;
        return this;
    }

    public ObjectRightType getObjectRightsType() {
        return objectRightType;
    }

    public BatchProductRightModel setObjectRightsType(ObjectRightType objectRightType) {
        this.objectRightType = objectRightType;
        return this;
    }
}
