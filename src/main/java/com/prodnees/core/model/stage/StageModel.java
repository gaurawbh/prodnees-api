package com.prodnees.core.model.stage;

import com.prodnees.core.domain.batch.RawProduct;
import com.prodnees.core.domain.enums.StageState;
import com.prodnees.core.domain.stage.StageTodo;

import java.util.List;

/**
 * Every Product Batch has a state.
 * <p>A Product Batch must have at least two states:</p>
 * <p> Initial State [A Batch Production Start Point]</p>
 * <p> Final State [A Batch Production Has Completed]</p>
 */
public class StageModel {
    List<StageApprovalDocumentModel> approvalDocumentList;
    List<StageTodo> stageTodoList;
    List<RawProduct> rawProductList;
    private int id;
    private int batchId;
    private int index;
    private String name;
    private String description;
    private StageState status;

    public List<StageApprovalDocumentModel> getApprovalDocumentList() {
        return approvalDocumentList;
    }

    public StageModel setApprovalDocumentList(List<StageApprovalDocumentModel> approvalDocumentList) {
        this.approvalDocumentList = approvalDocumentList;
        return this;
    }

    public List<StageTodo> getStageTodoList() {
        return stageTodoList;
    }

    public StageModel setStageTodoList(List<StageTodo> stageTodoList) {
        this.stageTodoList = stageTodoList;
        return this;
    }

    public List<RawProduct> getRawProductList() {
        return rawProductList;
    }

    public StageModel setRawProductList(List<RawProduct> rawProductList) {
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
