package com.prodnees.model.state;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prodnees.domain.enums.BatchStatus;
import com.prodnees.util.FormatUtil;
import java.time.LocalDate;

public class BatchStateListModel {

    private int id;
    private int productId;
    private String name;
    private String description;
    private BatchStatus status;
    @JsonFormat(pattern = FormatUtil.DATE)
    private LocalDate createdDate;

    public int getId() {
        return id;
    }

    public BatchStateListModel setId(int id) {
        this.id = id;
        return this;
    }

    public int getProductId() {
        return productId;
    }

    public BatchStateListModel setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public String getName() {
        return name;
    }

    public BatchStateListModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BatchStateListModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public BatchStatus getStatus() {
        return status;
    }

    public BatchStateListModel setStatus(BatchStatus status) {
        this.status = status;
        return this;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public BatchStateListModel setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }
}
