package com.prodnees.core.model.batch;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prodnees.core.domain.batch.Product;
import com.prodnees.core.domain.enums.BatchState;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.model.stage.StageModel;

import java.time.LocalDate;
import java.util.List;

/**
 * This is Product that is being Manufactured
 */
public class BatchModel {
    private int id;
    private Product product;
    private String name;
    private String description;
    private List<StageModel> stages;
    private BatchState status;
    private ObjectRight rightType;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    public int getId() {
        return id;
    }

    public BatchModel setId(int id) {
        this.id = id;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public BatchModel setProduct(Product product) {
        this.product = product;
        return this;
    }

    public String getName() {
        return name;
    }

    public BatchModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BatchModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<StageModel> getStages() {
        return stages;
    }

    public BatchModel setStages(List<StageModel> stages) {
        this.stages = stages;
        return this;
    }

    public BatchState getStatus() {
        return status;
    }

    public BatchModel setStatus(BatchState status) {
        this.status = status;
        return this;
    }

    public ObjectRight getRightType() {
        return rightType;
    }

    public BatchModel setRightType(ObjectRight rightType) {
        this.rightType = rightType;
        return this;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public BatchModel setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }
}
