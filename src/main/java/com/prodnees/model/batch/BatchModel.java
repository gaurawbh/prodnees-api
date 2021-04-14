package com.prodnees.model.batch;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prodnees.domain.enums.BatchStatus;
import com.prodnees.model.ProductModel;
import com.prodnees.model.state.StateApprovalDocumentModel;
import com.prodnees.model.state.StateModel;
import java.time.LocalDate;
import java.util.List;

/**
 * This is Product that is being Manufactured
 */
public class BatchModel {
    private int id;
    private ProductModel productModel;
    private String name;
    private String description;
    private List<StateModel> stateModelList;
    private StateApprovalDocumentModel stateApprovalDocumentModel;
    private BatchStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    public int getId() {
        return id;
    }

    public BatchModel setId(int id) {
        this.id = id;
        return this;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public BatchModel setProductModel(ProductModel productModel) {
        this.productModel = productModel;
        return this;
    }

    public List<StateModel> getStateModelList() {
        return stateModelList;
    }

    public BatchModel setStateModelList(List<StateModel> stateModelList) {
        this.stateModelList = stateModelList;
        return this;
    }

    public StateApprovalDocumentModel getApprovalDocumentModel() {
        return stateApprovalDocumentModel;
    }

    public BatchModel setApprovalDocumentModel(StateApprovalDocumentModel stateApprovalDocumentModel) {
        this.stateApprovalDocumentModel = stateApprovalDocumentModel;
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

    public BatchStatus getStatus() {
        return status;
    }

    public BatchModel setStatus(BatchStatus status) {
        this.status = status;
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
