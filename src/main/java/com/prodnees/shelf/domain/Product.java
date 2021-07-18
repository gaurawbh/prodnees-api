package com.prodnees.shelf.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodnees.core.config.annotations.NeesLabel;
import com.prodnees.core.config.constants.DateTimeFormats;
import org.springframework.data.annotation.Reference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Product that needs to be created
 */
@Entity
public class Product {
    @Id
    @GeneratedValue
    @NeesLabel("Id")
    private int id;
    @NeesLabel("Name")
    private String name;
    @NeesLabel("Description")
    private String description;
    @Reference(Supplier.class)
    @NeesLabel("Supplier Id")
    private Integer supplierId;
    @JsonFormat(pattern = DateTimeFormats.DATE)
    @NeesLabel("Added Date")
    private LocalDate addedDate;
    @NeesLabel("Price")
    private double price;
    @JsonProperty("priceHistory")
    private String priceHistoryJson;

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

    public double getPrice() {
        return price;
    }

    public Product setPrice(double price) {
        this.price = price;
        return this;
    }

    public List<Map<String, Object>> getPriceHistoryJson() throws JsonProcessingException {
        if (priceHistoryJson != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(priceHistoryJson, List.class);
        } else {
            return new ArrayList<>();
        }
    }

    public Product setPriceHistoryJson(List<Map<String, Object>> priceHistory) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.priceHistoryJson = objectMapper.writeValueAsString(priceHistory);
        return this;
    }

}
