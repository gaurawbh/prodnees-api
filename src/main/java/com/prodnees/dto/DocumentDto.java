package com.prodnees.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class DocumentDto {
    @Positive(message = "id must be a positive number")
    private int id;
    @NotBlank(message = "name cannot be null or blank")
    private String description;

    public int getId() {
        return id;
    }

    public DocumentDto setId(int id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DocumentDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
