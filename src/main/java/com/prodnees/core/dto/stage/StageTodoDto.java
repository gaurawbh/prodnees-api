package com.prodnees.core.dto.stage;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class StageTodoDto {

    private int id;
    @Positive(message = "stageId must be positive")
    @NotNull(message = "stageId must be provided")
    private int stageId;
    @NotBlank(message = "name cannot be null or blank")
    private String name;
    private String description;
    private boolean complete;

    public int getId() {
        return id;
    }

    public StageTodoDto setId(int id) {
        this.id = id;
        return this;
    }

    public int getStageId() {
        return stageId;
    }

    public StageTodoDto setStageId(int stageId) {
        this.stageId = stageId;
        return this;
    }

    public String getName() {
        return name;
    }

    public StageTodoDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StageTodoDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isComplete() {
        return complete;
    }

    public StageTodoDto setComplete(boolean complete) {
        this.complete = complete;
        return this;
    }
}
