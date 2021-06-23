package com.prodnees.core.domain.rels;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(StageRawProduct.class)
public class StageRawProduct implements Serializable {

    @Id
    private int stageId;
    @Id
    private int rawProductId;

    public int getStageId() {
        return stageId;
    }

    public StageRawProduct setStageId(int stateId) {
        this.stageId = stateId;
        return this;
    }

    public int getRawProductId() {
        return rawProductId;
    }

    public StageRawProduct setRawProductId(int rawProductId) {
        this.rawProductId = rawProductId;
        return this;
    }
}
