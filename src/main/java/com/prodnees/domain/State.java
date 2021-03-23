package com.prodnees.domain;

import javax.persistence.Entity;
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
    private boolean complete;
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

    public boolean isComplete() {
        return complete;
    }

    public State setComplete(boolean complete) {
        this.complete = complete;
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
