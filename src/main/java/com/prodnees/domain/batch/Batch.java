package com.prodnees.domain.batch;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prodnees.domain.enums.BatchState;
import com.prodnees.util.FormatUtil;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Batch {
    @Id
    @GeneratedValue
    private int id;
    private int productId;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private BatchState state;
    @JsonFormat(pattern = FormatUtil.DATE)
    private LocalDate createdDate;

    public int getId() {
        return id;
    }

    public Batch setId(int id) {
        this.id = id;
        return this;
    }

    public int getProductId() {
        return productId;
    }

    public Batch setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Batch setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Batch setDescription(String description) {
        this.description = description;
        return this;
    }

    public BatchState getState() {
        return state;
    }

    public Batch setState(BatchState batchState) {
        this.state = batchState;
        return this;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Batch setCreatedDate(LocalDate createDate) {
        this.createdDate = createDate;
        return this;
    }
}
