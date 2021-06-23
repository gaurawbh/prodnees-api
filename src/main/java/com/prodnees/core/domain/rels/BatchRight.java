package com.prodnees.core.domain.rels;

import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.domain.rels.id.BatchRightId;

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

    public BatchRight setBatchId(int batchId) {
        this.batchId = batchId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public BatchRight setUserId(int ownerId) {
        this.userId = ownerId;
        return this;
    }

    public ObjectRight getObjectRight() {
        return objectRight;
    }

    public BatchRight setObjectRight(ObjectRight objectRight) {
        this.objectRight = objectRight;
        return this;
    }
}
