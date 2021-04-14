package com.prodnees.dto.batch;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class BatchDto {

    private int id;
    @Positive(message = "productId is a required field must be a positive number")
    private int productId;
    @NotBlank(message = "name cannot be null or blank")
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public BatchDto setId(int id) {
        this.id = id;
        return this;
    }

    public int getProductId() {
        return productId;
    }

    public BatchDto setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public String getName() {
        return name;
    }

    public BatchDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BatchDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
