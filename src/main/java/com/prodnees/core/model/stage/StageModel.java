package com.prodnees.core.model.stage;

import com.prodnees.core.domain.enums.StageState;
import com.prodnees.core.domain.stage.StageReminder;
import com.prodnees.core.domain.stage.StageTodo;
import com.prodnees.shelf.domain.RawMaterial;

import java.util.List;

/**
 * Every Product Batch has a state.
 * <p>A Product Batch must have at least two states:</p>
 * <p> Initial State [A Batch Production Start Point]</p>
 * <p> Final State [A Batch Production Has Completed]</p>
 */
public class StageModel {
    private int id;
    private int batchId;
    private int index;
    private String name;
    private String description;
    private StageState status;
    private List<StageReminder> stageReminders;
    List<StageTodo> stageTodos;
    List<RawMaterial> rawMaterials;

    public List<StageTodo> getStageTodos() {
        return stageTodos;
    }

    public StageModel setStageTodos(List<StageTodo> stageTodos) {
        this.stageTodos = stageTodos;
        return this;
    }

    public List<RawMaterial> getRawProducts() {
        return rawMaterials;
    }

    public StageModel setRawProducts(List<RawMaterial> rawMaterials) {
        this.rawMaterials = rawMaterials;
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

    public List<StageReminder> getStageReminders() {
        return stageReminders;
    }

    public StageModel setStageReminders(List<StageReminder> stageReminders) {
        this.stageReminders = stageReminders;
        return this;
    }
}
