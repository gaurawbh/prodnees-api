package com.prodnees.core.dto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Map;

public class ProductDto {

    private int id;
    @NotBlank(message = "name cannot be null or blank")
    private String name;
    private String description;
    private Integer supplierId;
    private LocalDate addedDate;
    private double price;
    Map<String, Object> priceHistory;


    public int getId() {
        return id;
    }

    public ProductDto setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public ProductDto setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
        return this;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public ProductDto setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public ProductDto setPrice(double price) {
        this.price = price;
        return this;
    }

    public Map<String, Object> getPriceHistory() {
        return priceHistory;
    }

    public ProductDto setPriceHistory(Map<String, Object> priceHistory) {
        this.priceHistory = priceHistory;
        return this;
    }
}
