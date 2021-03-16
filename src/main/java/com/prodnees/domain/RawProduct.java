package com.prodnees.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedEntityGraph;

@Entity
public class RawProduct {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public RawProduct setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RawProduct setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RawProduct setDescription(String description) {
        this.description = description;
        return this;
    }
}
