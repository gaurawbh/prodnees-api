package com.prodnees.model.batch;

import com.prodnees.domain.batch.Batch;
import com.prodnees.domain.enums.ObjectRight;
import com.prodnees.model.user.UserModel;

public class BatchRightModel {

    private Batch batch;
    private UserModel userModel;
    private ObjectRight objectRight;

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

    public ObjectRight getObjectRightsType() {
        return objectRight;
    }

    public BatchRightModel setObjectRightsType(ObjectRight objectRight) {
        this.objectRight = objectRight;
        return this;
    }
}
