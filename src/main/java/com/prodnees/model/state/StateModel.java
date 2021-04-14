package com.prodnees.model.state;

import com.prodnees.domain.enums.StateStatus;
import com.prodnees.model.RawProductModel;
import java.util.List;

/**
 * Every Product Batch has a state.
 * <p>A Product Batch must have at least two states:</p>
 * <p> Initial State [A Batch Production Start Point]</p>
 * <p> Final State [A Batch Production Has Completed]</p>
 */
public class StateModel {
    List<StateApprovalDocumentModel> approvalDocuments;
    List<EventModel> eventModelList;
    List<RawProductModel> rawProductModelList;
    private int id;
    private int batchId;
    private int index;
    private String name;
    private String description;
    private StateStatus status;

    public int getId() {
        return id;
    }

    public StateModel setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchId() {
        return batchId;
    }

    public StateModel setBatchId(int batchId) {
        this.batchId = batchId;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public StateModel setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getName() {
        return name;
    }

    public StateModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StateModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<StateApprovalDocumentModel> getApprovalDocuments() {
        return approvalDocuments;
    }

    public StateModel setApprovalDocuments(List<StateApprovalDocumentModel> approvalDocuments) {
        this.approvalDocuments = approvalDocuments;
        return this;
    }

    public List<EventModel> getEventModelList() {
        return eventModelList;
    }

    public StateModel setEventModelList(List<EventModel> eventModelList) {
        this.eventModelList = eventModelList;
        return this;
    }

    public List<RawProductModel> getRawProductModelList() {
        return rawProductModelList;
    }

    public StateModel setRawProductModelList(List<RawProductModel> rawProductModelList) {
        this.rawProductModelList = rawProductModelList;
        return this;
    }

    public StateStatus getStatus() {
        return status;
    }

    public StateModel setStatus(StateStatus status) {
        this.status = status;
        return this;
    }

}
