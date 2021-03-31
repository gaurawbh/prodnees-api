package com.prodnees.model;

import java.util.List;

/**
 * Every Product Batch has a state.
 * <p>A Product Batch must have at least two states:</p>
 * <p> Initial State [A Batch Production Start Point]</p>
 * <p> Final State [A Batch Production Has Completed]</p>
 */
public class StateModel {
    private int id;
    private int batchProductId;
    private String name;
    private String description;
    List<StateApprovalDocumentModel> approvalDocuments;
    List<EventModel> eventModelList;
    List<RawProductModel> rawProductModelList;
    private boolean complete;
    private int lastStateId;
    private int nextStateId;
    private boolean initialState;
    private boolean finalState;

    public int getId() {
        return id;
    }

    public StateModel setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchProductId() {
        return batchProductId;
    }

    public StateModel setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
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

    public boolean isComplete() {
        return complete;
    }

    public StateModel setComplete(boolean complete) {
        this.complete = complete;
        return this;
    }

    public int getLastStateId() {
        return lastStateId;
    }

    public StateModel setLastStateId(int lastStateId) {
        this.lastStateId = lastStateId;
        return this;
    }

    public int getNextStateId() {
        return nextStateId;
    }

    public StateModel setNextStateId(int nextStateId) {
        this.nextStateId = nextStateId;
        return this;
    }

    public boolean isInitialState() {
        return initialState;
    }

    public StateModel setInitialState(boolean initialState) {
        this.initialState = initialState;
        return this;
    }

    public boolean isFinalState() {
        return finalState;
    }

    public StateModel setFinalState(boolean finalState) {
        this.finalState = finalState;
        return this;
    }
}
