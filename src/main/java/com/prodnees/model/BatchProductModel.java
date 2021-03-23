package com.prodnees.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prodnees.domain.BatchProductStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * This is Product that is being Manufactured
 */
public class BatchProductModel {
    private int id;
    private ProductModel productModel;
    private String name;
    private String description;
    private List<StateModel> stateModelList;
    private ApprovalDocumentModel approvalDocumentModel;
    private BatchProductStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    public int getId() {
        return id;
    }

    public BatchProductModel setId(int id) {
        this.id = id;
        return this;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public BatchProductModel setProductModel(ProductModel productModel) {
        this.productModel = productModel;
        return this;
    }

    public List<StateModel> getStateModelList() {
        return stateModelList;
    }

    public BatchProductModel setStateModelList(List<StateModel> stateModelList) {
        this.stateModelList = stateModelList;
        return this;
    }

    public ApprovalDocumentModel getApprovalDocumentModel() {
        return approvalDocumentModel;
    }

    public BatchProductModel setApprovalDocumentModel(ApprovalDocumentModel approvalDocumentModel) {
        this.approvalDocumentModel = approvalDocumentModel;
        return this;
    }

    public String getName() {
        return name;
    }

    public BatchProductModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BatchProductModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public BatchProductStatus getStatus() {
        return status;
    }

    public BatchProductModel setStatus(BatchProductStatus status) {
        this.status = status;
        return this;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public BatchProductModel setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }


}
