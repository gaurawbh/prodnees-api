package com.prodnees.model;

import com.prodnees.domain.batchproduct.Batch;
import com.prodnees.domain.enums.ObjectRightType;

public class BatchProductRightModel {

    private Batch batch;
    private UserModel userModel;
    private ObjectRightType objectRightType;

    public Batch getBatchProduct() {
        return batch;
    }

    public BatchProductRightModel setBatchProduct(Batch batch) {
        this.batch = batch;
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
