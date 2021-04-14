package com.prodnees.model.batch;

import com.prodnees.domain.batch.Batch;
import com.prodnees.domain.enums.ObjectRightType;
import com.prodnees.model.user.UserModel;

public class BatchRightModel {

    private Batch batch;
    private UserModel userModel;
    private ObjectRightType objectRightType;

    public Batch getBatchProduct() {
        return batch;
    }

    public BatchRightModel setBatchProduct(Batch batch) {
        this.batch = batch;
        return this;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public BatchRightModel setUserModel(UserModel userModel) {
        this.userModel = userModel;
        return this;
    }

    public ObjectRightType getObjectRightsType() {
        return objectRightType;
    }

    public BatchRightModel setObjectRightsType(ObjectRightType objectRightType) {
        this.objectRightType = objectRightType;
        return this;
    }
}
