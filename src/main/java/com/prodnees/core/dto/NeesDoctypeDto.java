package com.prodnees.core.dto;

import javax.validation.constraints.NotBlank;

public class NeesDoctypeDto {
    private int id;
    @NotBlank(message = "name is a required field")
    private String name;
    private String description;
    private String[] subTypes;

    public int getId() {
        return id;
    }

    public NeesDoctypeDto setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public NeesDoctypeDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public NeesDoctypeDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String[] getSubTypes() {
        return subTypes;
    }

    public NeesDoctypeDto setSubTypes(String[] subTypes) {
        this.subTypes = subTypes;
        return this;
    }
}
