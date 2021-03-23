package com.prodnees.domain.rels;

import com.prodnees.domain.rels.id.BatchProductRightId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(BatchProductRightId.class)
public class BatchProductRight implements Serializable {
    @Id
    private int batchProductId;
    @Id
    private int userId;
    private ObjectRightType objectRightType;

    public int getBatchProductId() {
        return batchProductId;
    }

    public BatchProductRight setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public BatchProductRight setUserId(int ownerId) {
        this.userId = ownerId;
        return this;
    }

    public ObjectRightType getObjectRightsType() {
        return objectRightType;
    }

    public BatchProductRight setObjectRightsType(ObjectRightType objectRightType) {
        this.objectRightType = objectRightType;
        return this;
    }
}
