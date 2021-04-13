package com.prodnees.dto.state;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class StateDto {

    private int id;
    @Positive(message = "batchProductId is a required field must be a positive number")
    private int batchProductId;
    @NotBlank(message = "name cannot be null or blank")
    private String name;
    private String description;
    private int lastStateId;
    private int nextStateId;
    private boolean initialState;
    private boolean finalState;

    public int getId() {
        return id;
    }

    public StateDto setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchProductId() {
        return batchProductId;
    }

    public StateDto setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
        return this;
    }

    public String getName() {
        return name;
    }

    public StateDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StateDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getLastStateId() {
        return lastStateId;
    }

    public StateDto setLastStateId(int lastStateId) {
        this.lastStateId = lastStateId;
        return this;
    }

    public int getNextStateId() {
        return nextStateId;
    }

    public StateDto setNextStateId(int nextStateId) {
        this.nextStateId = nextStateId;
        return this;
    }

    public boolean isInitialState() {
        return initialState;
    }

    public StateDto setInitialState(boolean initialState) {
        this.initialState = initialState;
        return this;
    }

    public boolean isFinalState() {
        return finalState;
    }

    public StateDto setFinalState(boolean finalState) {
        this.finalState = finalState;
        return this;
    }
}
