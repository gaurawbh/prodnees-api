package com.prodnees.model.batch;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prodnees.domain.batch.Product;
import com.prodnees.domain.enums.BatchState;
import com.prodnees.domain.enums.ObjectRight;
import com.prodnees.model.stage.StageApprovalDocumentModel;
import com.prodnees.model.stage.StageModel;

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
    private List<StageModel> stageList;
    private StageApprovalDocumentModel stageApprovalDocument;
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

    public StageApprovalDocumentModel getStateApprovalDocumentModel() {
        return stageApprovalDocument;
    }

    public BatchModel setStateApprovalDocumentModel(StageApprovalDocumentModel stageApprovalDocumentModel) {
        this.stageApprovalDocument = stageApprovalDocumentModel;
        return this;
    }

    public ObjectRight getRightType() {
        return rightType;
    }

    public BatchModel setRightType(ObjectRight rightType) {
        this.rightType = rightType;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public BatchModel setProduct(Product product) {
        this.product = product;
        return this;
    }

    public List<StageModel> getStageList() {
        return stageList;
    }

    public BatchModel setStageList(List<StageModel> stageList) {
        this.stageList = stageList;
        return this;
    }

    public List<StageModel> getStateModelList() {
        return stageList;
    }

    public BatchModel setStateModelList(List<StageModel> stageModelList) {
        this.stageList = stageModelList;
        return this;
    }

    public StageApprovalDocumentModel getStageApprovalDocument() {
        return stageApprovalDocument;
    }

    public BatchModel setStageApprovalDocument(StageApprovalDocumentModel stageApprovalDocument) {
        this.stageApprovalDocument = stageApprovalDocument;
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

    public BatchState getStatus() {
        return status;
    }

    public BatchModel setStatus(BatchState status) {
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
