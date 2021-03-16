package com.prodnees.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

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
}
