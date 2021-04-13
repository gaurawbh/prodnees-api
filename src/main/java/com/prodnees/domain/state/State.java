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
    private int batchProductId;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private StateStatus status;
    private int lastStateId;
    private int nextStateId;
    private boolean initialState;
    private boolean finalState;

    public int getId() {
        return id;
    }

    public State setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchProductId() {
        return batchProductId;
    }

    public State setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
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

    public int getLastStateId() {
        return lastStateId;
    }

    public State setLastStateId(int lastStateId) {
        this.lastStateId = lastStateId;
        return this;
    }

    public int getNextStateId() {
        return nextStateId;
    }

    public State setNextStateId(int nextStateId) {
        this.nextStateId = nextStateId;
        return this;
    }

    public boolean isInitialState() {
        return initialState;
    }

    public State setInitialState(boolean initialState) {
        this.initialState = initialState;
        return this;
    }

    public boolean isFinalState() {
        return finalState;
    }

    public State setFinalState(boolean finalState) {
        this.finalState = finalState;
        return this;
    }
}
