package com.prodnees.domain.stage;

import com.prodnees.domain.enums.StageState;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Future Enhancements:
 * <p>add a reminder when a State is complete</p>
 * <p>schedule emails when a State is complete</p>
 */

@Entity
public class Stage {
    @Id
    @GeneratedValue
    private int id;
    private int batchId;
    @Column(name = "indx")
    private int index;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private StageState state;

    public int getId() {
        return id;
    }

    public Stage setId(int id) {
        this.id = id;
        return this;
    }

    public int getBatchId() {
        return batchId;
    }

    public Stage setBatchId(int batchProductId) {
        this.batchId = batchProductId;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public Stage setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getName() {
        return name;
    }

    public Stage setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Stage setDescription(String description) {
        this.description = description;
        return this;
    }

    public StageState getState() {
        return state;
    }

    public Stage setState(StageState status) {
        this.state = status;
        return this;
    }

}
