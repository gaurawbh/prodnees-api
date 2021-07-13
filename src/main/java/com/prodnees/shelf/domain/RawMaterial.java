package com.prodnees.shelf.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RawMaterial {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public RawMaterial setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RawMaterial setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RawMaterial setDescription(String description) {
        this.description = description;
        return this;
    }
}
