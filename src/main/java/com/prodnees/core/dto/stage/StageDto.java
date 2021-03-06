package com.prodnees.core.dto.stage;

import javax.validation.constraints.Positive;

public class StageDto {

    private int id;
    @Positive(message = "batchId is a required field must be a positive number")
    private int batchId;
    private Integer index = -1; //if no index is provided in request body, it should be added on the top of the StageList
    private String description;

    public int getId() {
        return id;
    }

    public StageDto setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchId() {
        return batchId;
    }

    public StageDto setBatchId(int batchId) {
        this.batchId = batchId;
        return this;
    }

    public Integer getIndex() {
        return index;
    }

    public StageDto setIndex(Integer index) {
        this.index = index;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StageDto setDescription(String description) {
        this.description = description;
        return this;
    }

}
