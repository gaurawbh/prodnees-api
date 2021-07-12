package com.prodnees.core.model.batch;

import com.prodnees.core.domain.batch.Batch;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.model.user.NeesUserDetails;

public class BatchRightModel {

    private Batch batch;
    private NeesUserDetails user;
    private ObjectRight objectRight;

    public Batch getBatchProduct() {
        return batch;
    }

    public BatchRightModel setBatchProduct(Batch batch) {
        this.batch = batch;
        return this;
    }

    public NeesUserDetails getUser() {
        return user;
    }

    public BatchRightModel setUser(NeesUserDetails user) {
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
