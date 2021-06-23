package com.prodnees.qc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InspectionComponent {
    private int id;
    private String name;
    @JsonProperty(value = "fields")
    private String fieldsJson;
}
