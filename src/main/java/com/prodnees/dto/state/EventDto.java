package com.prodnees.dto.state;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class EventDto {

    private int id;
    @Positive(message = "batchProductId must be positive")
    private int batchId;
    @Positive(message = "stateId must be positive")
    private int stageId;
    @NotBlank(message = "name cannot be null or blank")
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public EventDto setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchId() {
        return batchId;
    }

    public EventDto setBatchId(int batchId) {
        this.batchId = batchId;
        return this;
    }

    public int getStageId() {
        return stageId;
    }

    public EventDto setStageId(int stageId) {
        this.stageId = stageId;
        return this;
    }

    public String getName() {
        return name;
    }

    public EventDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventDto setDescription(String description) {
        this.description = description;
        return this;
    }

}
