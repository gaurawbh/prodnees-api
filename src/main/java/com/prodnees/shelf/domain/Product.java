package com.prodnees.shelf.domain;

import org.springframework.data.annotation.Reference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Product that needs to be created
 */
@Entity
public class Product {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
    @Reference(Supplier.class)
    private Integer supplierId;
    private LocalDate addedDate;

    public int getId() {
        return id;
    }

    public Product setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public Product setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
        return this;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public Product setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
        return this;
    }
}
