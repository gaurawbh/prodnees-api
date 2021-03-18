package com.prodnees.domain.rels;

import com.prodnees.domain.rels.id.BatchProductRightsId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(BatchProductRightsId.class)
public class BatchProductRights implements Serializable {
    @Id
    private int batchProductId;
    @Id
    private int userId;
    private ObjectRightsType objectRightsType;

    public int getBatchProductId() {
        return batchProductId;
    }

    public BatchProductRights setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public BatchProductRights setUserId(int ownerId) {
        this.userId = ownerId;
        return this;
    }

    public ObjectRightsType getObjectRightsType() {
        return objectRightsType;
    }

    public BatchProductRights setObjectRightsType(ObjectRightsType objectRightsType) {
        this.objectRightsType = objectRightsType;
        return this;
    }
}
