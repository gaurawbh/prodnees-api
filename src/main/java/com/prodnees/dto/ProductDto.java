package com.prodnees.dto;

import javax.validation.constraints.NotBlank;

public class ProductDto {

    private int id;
    @NotBlank(message = "name cannot be null or blank")
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public ProductDto setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
