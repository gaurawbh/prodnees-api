package com.prodnees.core.domain.doc;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NeesDoctype {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    @JsonProperty("subTypes")
    private String subTypesJson;
    private boolean active;
    private boolean sys; // if it is system, users cannot delete it

    public int getId() {
        return id;
    }

    public NeesDoctype setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public NeesDoctype setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public NeesDoctype setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getSubTypesJson() {
        return subTypesJson;
    }

    public NeesDoctype setSubTypesJson(String subTypesJson) {
        this.subTypesJson = subTypesJson;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public NeesDoctype setActive(boolean active) {
        this.active = active;
        return this;
    }

    public boolean isSys() {
        return sys;
    }

    public NeesDoctype setSys(boolean system) {
        this.sys = system;
        return this;
    }
}
