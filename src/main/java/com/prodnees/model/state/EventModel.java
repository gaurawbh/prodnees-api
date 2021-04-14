package com.prodnees.model.state;

public class EventModel {

    private int id;
    private int batchProductId;
    private int stateId;
    private String name;
    private String description;
    private boolean complete;

    public int getId() {
        return id;
    }

    public EventModel setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchProductId() {
        return batchProductId;
    }

    public EventModel setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
        return this;
    }

    public int getStateId() {
        return stateId;
    }

    public EventModel setStateId(int stateId) {
        this.stateId = stateId;
        return this;
    }

    public String getName() {
        return name;
    }

    public EventModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isComplete() {
        return complete;
    }

    public EventModel setComplete(boolean complete) {
        this.complete = complete;
        return this;
    }
}
