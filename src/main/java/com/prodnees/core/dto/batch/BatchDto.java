package com.prodnees.core.dto.batch;

import javax.validation.constraints.Positive;

public class BatchDto {

    private int id;
    @Positive(message = "productId is a required field must be a positive number")
    private int productId;
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

    public String getDescription() {
        return description;
    }

    public BatchDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
