package com.prodnees.core.model.batch;

import com.prodnees.core.domain.batch.Batch;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.model.user.UserModel;

public class BatchRightModel {

    private Batch batch;
    private UserModel user;
    private ObjectRight objectRight;

    public Batch getBatchProduct() {
        return batch;
    }

    public BatchRightModel setBatchProduct(Batch batch) {
        this.batch = batch;
        return this;
    }

    public UserModel getUser() {
        return user;
    }

    public BatchRightModel setUser(UserModel user) {
        this.user = user;
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
