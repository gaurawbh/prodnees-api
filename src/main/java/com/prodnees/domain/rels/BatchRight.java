package com.prodnees.domain.rels;

import com.prodnees.domain.enums.ObjectRight;
import com.prodnees.domain.rels.id.BatchRightId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(BatchRightId.class)
public class BatchRight implements Serializable {
    @Id
    private int batchId;
    @Id
    private int userId;
    private ObjectRight objectRight;

    public int getBatchId() {
        return batchId;
    }

    public BatchRight setBatchId(int batchProductId) {
        this.batchId = batchProductId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public BatchRight setUserId(int ownerId) {
        this.userId = ownerId;
        return this;
    }

    public ObjectRight getObjectRightsType() {
        return objectRight;
    }

    public BatchRight setObjectRightsType(ObjectRight objectRight) {
        this.objectRight = objectRight;
        return this;
    }
}
