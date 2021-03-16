package com.prodnees.domain.rels;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(BatchProductOwner.class)
public class BatchProductOwner implements Serializable {
    @Id
    private int batchProductId;
    @Id
    private int ownerId;

    public int getBatchProductId() {
        return batchProductId;
    }

    public BatchProductOwner setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
        return this;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public BatchProductOwner setOwnerId(int ownerId) {
        this.ownerId = ownerId;
        return this;
    }
}
