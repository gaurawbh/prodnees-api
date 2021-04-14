package com.prodnees.dto.state;

import javax.validation.constraints.Positive;

public class StateDto {

    private int id;
    @Positive(message = "batchId is a required field must be a positive number")
    private int batchId;
    private Integer index;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public StateDto setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchId() {
        return batchId;
    }

    public StateDto setBatchId(int batchId) {
        this.batchId = batchId;
        return this;
    }

    public Integer getIndex() {
        return index;
    }

    public StateDto setIndex(Integer index) {
        this.index = index;
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

}
