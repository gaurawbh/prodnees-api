package com.prodnees.model;

public class ProductModel {

    private int id;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public ProductModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
