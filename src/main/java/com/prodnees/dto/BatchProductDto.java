package com.prodnees.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class BatchProductDto {

    private int id;
    @Positive(message = "productId is a required field must be a positive number")
    private int productId;
    @NotBlank(message = "name cannot be null or blank")
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public BatchProductDto setId(int id) {
        this.id = id;
        return this;
    }

    public int getProductId() {
        return productId;
    }

    public BatchProductDto setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public String getName() {
        return name;
    }

    public BatchProductDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BatchProductDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
