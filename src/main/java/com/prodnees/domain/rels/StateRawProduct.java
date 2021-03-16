package com.prodnees.domain.rels;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(StateRawProduct.class)
public class StateRawProduct implements Serializable {

    @Id
    private int stateId;
    @Id
    private int rawProductId;

    public int getStateId() {
        return stateId;
    }

    public StateRawProduct setStateId(int stateId) {
        this.stateId = stateId;
        return this;
    }

    public int getRawProductId() {
        return rawProductId;
    }

    public StateRawProduct setRawProductId(int rawProductId) {
        this.rawProductId = rawProductId;
        return this;
    }
}
