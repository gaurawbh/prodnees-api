package com.prodnees.model;

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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public BatchProductModel setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }
}
