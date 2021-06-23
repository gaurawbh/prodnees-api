package com.prodnees.qc.dto;

import javax.validation.constraints.NotBlank;

public class InspectionTypeDto {
    private int id;
    @NotBlank(message = "name is a required field")
    private String name;
    private String summary;
}
