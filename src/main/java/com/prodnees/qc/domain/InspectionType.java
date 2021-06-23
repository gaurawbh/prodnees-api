package com.prodnees.qc.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * <p>Below are the standard inspection types</p>
 * <p>1: Pre-production inspection</p>
 * <p>2: During production inspection</p>
 * <p>3: Final random inspection</p>
 * <i> Users can remove update these the standard inspection types</i>
 * <p><i>In addition, they can add their own inspection types</i></p>
 */
@Entity
public class InspectionType {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String summary;
    private Integer createdBy;
    private String createdByName; // standard types will be added by the System.

    public int getId() {
        return id;
    }

    public InspectionType setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public InspectionType setName(String name) {
        this.name = name;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public InspectionType setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public InspectionType setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public InspectionType setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
        return this;
    }
}
