package com.prodnees.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BatchProduct {
    @Id
    @GeneratedValue
    private int id;
    private int productId;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public BatchProduct setId(int id) {
        this.id = id;
        return this;
    }

    public int getProductId() {
        return productId;
    }

    public BatchProduct setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public String getName() {
        return name;
    }

    public BatchProduct setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BatchProduct setDescription(String description) {
        this.description = description;
        return this;
    }
}
