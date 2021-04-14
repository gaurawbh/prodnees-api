package com.prodnees.dto.state;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class StateDto {

    private int id;
    @Positive(message = "batchProductId is a required field must be a positive number")
    private int batchProductId;
    @NotBlank(message = "name cannot be null or blank")
    private int index;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public StateDto setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchProductId() {
        return batchProductId;
    }

    public StateDto setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public StateDto setIndex(int index) {
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
