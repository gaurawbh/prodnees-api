package com.prodnees.model.stage;

import com.prodnees.domain.enums.StageState;
import com.prodnees.model.RawProductModel;

import java.util.List;

/**
 * Every Product Batch has a state.
 * <p>A Product Batch must have at least two states:</p>
 * <p> Initial State [A Batch Production Start Point]</p>
 * <p> Final State [A Batch Production Has Completed]</p>
 */
public class StageModel {
    List<StageApprovalDocumentModel> approvalDocuments;
    List<StageTodoModel> stageTodoList;
    List<RawProductModel> rawProductList;
    private int id;
    private int batchId;
    private int index;
    private String name;
    private String description;
    private StageState status;

    public List<StageApprovalDocumentModel> getApprovalDocuments() {
        return approvalDocuments;
    }

    public StageModel setApprovalDocuments(List<StageApprovalDocumentModel> approvalDocuments) {
        this.approvalDocuments = approvalDocuments;
        return this;
    }

    public List<StageTodoModel> getStageTodoList() {
        return stageTodoList;
    }

    public StageModel setStageTodoList(List<StageTodoModel> stageTodoList) {
        this.stageTodoList = stageTodoList;
        return this;
    }

    public List<RawProductModel> getRawProductList() {
        return rawProductList;
    }

    public StageModel setRawProductList(List<RawProductModel> rawProductList) {
        this.rawProductList = rawProductList;
        return this;
    }

    public int getId() {
        return id;
    }

    public StageModel setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchId() {
        return batchId;
    }

    public StageModel setBatchId(int batchId) {
        this.batchId = batchId;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public StageModel setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getName() {
        return name;
    }

    public StageModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StageModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public StageState getStatus() {
        return status;
    }

    public StageModel setStatus(StageState status) {
        this.status = status;
        return this;
    }
}
