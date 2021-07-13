package com.prodnees.shelf.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class ProductGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String privateKey;
    @NotBlank(message = "label is a required field")
    private String label;
    private String description;
    private boolean active;
    private boolean sys;

    public int getId() {
        return id;
    }

    public ProductGroup setId(int id) {
        this.id = id;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public ProductGroup setPrivateKey(String key) {
        this.privateKey = key;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public ProductGroup setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductGroup setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public ProductGroup setActive(boolean active) {
        this.active = active;
        return this;
    }

    public boolean isSys() {
        return sys;
    }

    public ProductGroup setSys(boolean sys) {
        this.sys = sys;
        return this;
    }
}
