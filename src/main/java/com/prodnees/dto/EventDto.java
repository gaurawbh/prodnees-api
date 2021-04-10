package com.prodnees.dto;

import org.aspectj.bridge.IMessage;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class EventDto {

    private int id;
    @Positive(message = "batchProductId must be positive")
    private int batchProductId;
    @Positive(message = "stateId must be positive")
    private int stateId;
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

    public int getBatchProductId() {
        return batchProductId;
    }

    public EventDto setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
        return this;
    }

    public int getStateId() {
        return stateId;
    }

    public EventDto setStateId(int stateId) {
        this.stateId = stateId;
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
