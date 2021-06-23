package com.prodnees.qc.dto;

import javax.validation.constraints.NotBlank;

public class InspectionTypeDto {
    private Integer id;
    @NotBlank(message = "name is a required field")
    private String name;
    private String summary;

    public Integer getId() {
        return id;
    }

    public InspectionTypeDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public InspectionTypeDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public InspectionTypeDto setSummary(String summary) {
        this.summary = summary;
        return this;
    }
}
