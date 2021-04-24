package com.prodnees.model.batch;

import com.prodnees.domain.batch.Batch;

import java.util.List;

public class BatchListModel {
    private int count;
    private int open;
    private int inProgress;
    private int complete;
    private int suspended;
    private List<Batch> batches;

    public int getCount() {
        return count;
    }

    public BatchListModel setCount(int count) {
        this.count = count;
        return this;
    }

    public int getOpen() {
        return open;
    }

    public BatchListModel setOpen(int open) {
        this.open = open;
        return this;
    }

    public int getInProgress() {
        return inProgress;
    }

    public BatchListModel setInProgress(int inProgress) {
        this.inProgress = inProgress;
        return this;
    }

    public int getComplete() {
        return complete;
    }

    public BatchListModel setComplete(int complete) {
        this.complete = complete;
        return this;
    }

    public int getSuspended() {
        return suspended;
    }

    public BatchListModel setSuspended(int suspended) {
        this.suspended = suspended;
        return this;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public BatchListModel setBatches(List<Batch> batches) {
        this.batches = batches;
        return this;
    }
}
