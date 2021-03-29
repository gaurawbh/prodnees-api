package com.prodnees.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class DocumentDto {
    @Positive(message = "id must be a positive number")
    private int id;
    @NotBlank(message = "name cannot be null or blank")
    private String name;

    public int getId() {
        return id;
    }

    public DocumentDto setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DocumentDto setName(String name) {
        this.name = name;
        return this;
    }
}
