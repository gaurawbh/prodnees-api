package com.prodnees.core.dto;

import com.prodnees.core.domain.user.NeesObject;
import com.prodnees.qc.domain.ValueType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ObjectAttributeDto {
    private Integer id;
    @NotNull(message = "neesObject is a required field")
    private NeesObject neesObject;
    @NotBlank(message = "label is a required field")
    private String label;
    private String helpContent;
    @NotNull(message = "valueType is a required field")
    private ValueType valueType;
    @NotNull(message = "required is a required field")
    private Boolean required;

    public Integer getId() {
        return id;
    }

    public ObjectAttributeDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public NeesObject getNeesObject() {
        return neesObject;
    }

    public ObjectAttributeDto setNeesObject(NeesObject neesObject) {
        this.neesObject = neesObject;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public ObjectAttributeDto setLabel(String label) {
        this.label = label;
        return this;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public ObjectAttributeDto setValueType(ValueType valueType) {
        this.valueType = valueType;
        return this;
    }

    public Boolean getRequired() {
        return required;
    }

    public ObjectAttributeDto setRequired(Boolean required) {
        this.required = required;
        return this;
    }

    public String getHelpContent() {
        return helpContent;
    }

    public ObjectAttributeDto setHelpContent(String helpContent) {
        this.helpContent = helpContent;
        return this;
    }
}
