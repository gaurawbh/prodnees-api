package com.prodnees.core.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class NeesDocDto {
    @Positive(message = "id must be a positive number")
    private int id;
    @NotBlank(message = "name cannot be null or blank")
    private String name;
    private String description;
    @Positive(message = "objectId must be a positive number")
    private Integer objectId;

    public int getId() {
        return id;
    }

    public NeesDocDto setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public NeesDocDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public NeesDocDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public NeesDocDto setObjectId(Integer objectId) {
        this.objectId = objectId;
        return this;
    }
}
