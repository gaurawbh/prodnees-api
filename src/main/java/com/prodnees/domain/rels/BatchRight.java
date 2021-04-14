package com.prodnees.domain.rels;

import com.prodnees.domain.enums.ObjectRightType;
import com.prodnees.domain.rels.id.BatchRightId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(BatchRightId.class)
public class BatchRight implements Serializable {
    @Id
    private int batchProductId;
    @Id
    private int userId;
    private ObjectRightType objectRightType;

    public int getBatchProductId() {
        return batchProductId;
    }

    public BatchRight setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public BatchRight setUserId(int ownerId) {
        this.userId = ownerId;
        return this;
    }

    public ObjectRightType getObjectRightsType() {
        return objectRightType;
    }

    public BatchRight setObjectRightsType(ObjectRightType objectRightType) {
        this.objectRightType = objectRightType;
        return this;
    }
}
