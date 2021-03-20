package com.prodnees.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prodnees.domain.BatchProductStatus;
import com.prodnees.domain.Product;
import java.time.LocalDate;

/**
 * This is Product that is being Manufactured
 */
public class BatchProductModel {
    private int id;
    private Product product;
    private String name;
    private String description;
    private BatchProductStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    public int getId() {
        return id;
    }

    public BatchProductModel setId(int id) {
        this.id = id;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public BatchProductModel setProduct(Product product) {
        this.product = product;
        return this;
    }

    public String getName() {
        return name;
    }

    public BatchProductModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BatchProductModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public BatchProductStatus getStatus() {
        return status;
    }

    public BatchProductModel setStatus(BatchProductStatus status) {
        this.status = status;
        return this;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public BatchProductModel setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }
}
