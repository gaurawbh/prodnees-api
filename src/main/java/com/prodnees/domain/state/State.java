package com.prodnees.domain.state;

import com.prodnees.domain.enums.StateStatus;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Future Enhancements:
 * <p>add a reminder when a State is complete</p>
 * <p>schedule emails when a State is complete</p>
 */

@Entity
public class State {
    @Id
    @GeneratedValue
    private int id;
    private int batchId;
    private int index;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private StateStatus status;

    public int getId() {
        return id;
    }

    public State setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchId() {
        return batchId;
    }

    public State setBatchId(int batchProductId) {
        this.batchId = batchProductId;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public State setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getName() {
        return name;
    }

    public State setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public State setDescription(String description) {
        this.description = description;
        return this;
    }

    public StateStatus getStatus() {
        return status;
    }

    public State setStatus(StateStatus status) {
        this.status = status;
        return this;
    }

}
