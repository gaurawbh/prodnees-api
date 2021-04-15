package com.prodnees.model.state;

public class EventModel {

    private int id;
    private int batchId;
    private int stageId;
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

    public int getBatchId() {
        return batchId;
    }

    public EventModel setBatchId(int batchId) {
        this.batchId = batchId;
        return this;
    }

    public int getStageId() {
        return stageId;
    }

    public EventModel setStageId(int stageId) {
        this.stageId = stageId;
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
