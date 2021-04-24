package com.prodnees.model.stage;

public class StageTodoModel {

    private int id;
    private int batchId;
    private int stageId;
    private String name;
    private String description;
    private boolean complete;

    public int getId() {
        return id;
    }

    public StageTodoModel setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchId() {
        return batchId;
    }

    public StageTodoModel setBatchId(int batchId) {
        this.batchId = batchId;
        return this;
    }

    public int getStageId() {
        return stageId;
    }

    public StageTodoModel setStageId(int stageId) {
        this.stageId = stageId;
        return this;
    }

    public String getName() {
        return name;
    }

    public StageTodoModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StageTodoModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isComplete() {
        return complete;
    }

    public StageTodoModel setComplete(boolean complete) {
        this.complete = complete;
        return this;
    }
}
