package com.prodnees.core.domain.stage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class StageTodo {
    @Id
    @GeneratedValue
    private int id;
    private int batchId;
    private int stageId;
    private String name;
    private String description;
    private boolean complete;

    public int getId() {
        return id;
    }

    public StageTodo setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchId() {
        return batchId;
    }

    public StageTodo setBatchId(int batchProductId) {
        this.batchId = batchProductId;
        return this;
    }

    public int getStageId() {
        return stageId;
    }

    public StageTodo setStageId(int stateId) {
        this.stageId = stateId;
        return this;
    }

    public String getName() {
        return name;
    }

    public StageTodo setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StageTodo setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isComplete() {
        return complete;
    }

    public StageTodo setComplete(boolean complete) {
        this.complete = complete;
        return this;
    }
}
