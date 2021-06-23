package com.prodnees.core.model.stage;

import com.prodnees.core.domain.stage.Stage;

import java.util.List;

public class BatchStageListModel {

    private int batchId;
    private int count;
    private int open;
    private int inProgress;
    private int complete;
    private List<Stage> stages;

    public int getBatchId() {
        return batchId;
    }

    public BatchStageListModel setBatchId(int batchId) {
        this.batchId = batchId;
        return this;
    }

    public int getCount() {
        return count;
    }

    public BatchStageListModel setCount(int count) {
        this.count = count;
        return this;
    }

    public int getOpen() {
        return open;
    }

    public BatchStageListModel setOpen(int open) {
        this.open = open;
        return this;
    }

    public int getInProgress() {
        return inProgress;
    }

    public BatchStageListModel setInProgress(int inProgress) {
        this.inProgress = inProgress;
        return this;
    }

    public int getComplete() {
        return complete;
    }

    public BatchStageListModel setComplete(int complete) {
        this.complete = complete;
        return this;
    }

    public List<Stage> getStates() {
        return stages;
    }

    public BatchStageListModel setStates(List<Stage> stages) {
        this.stages = stages;
        return this;
    }
}
