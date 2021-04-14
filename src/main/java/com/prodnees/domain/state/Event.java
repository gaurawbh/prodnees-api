package com.prodnees.domain.state;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Event {
    @Id
    @GeneratedValue
    private int id;
    private int batchId;
    private int stateId;
    private String name;
    private String description;
    private boolean complete;

    public int getId() {
        return id;
    }

    public Event setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchId() {
        return batchId;
    }

    public Event setBatchId(int batchProductId) {
        this.batchId = batchProductId;
        return this;
    }

    public int getStateId() {
        return stateId;
    }

    public Event setStateId(int stateId) {
        this.stateId = stateId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Event setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Event setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isComplete() {
        return complete;
    }

    public Event setComplete(boolean complete) {
        this.complete = complete;
        return this;
    }
}
