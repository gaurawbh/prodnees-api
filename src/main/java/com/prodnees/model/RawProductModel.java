package com.prodnees.model;

public class RawProductModel {

    private int id;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public RawProductModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RawProductModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RawProductModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
