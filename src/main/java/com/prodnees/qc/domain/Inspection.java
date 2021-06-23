package com.prodnees.qc.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Inspection {
    private int id;
    private int batchId;
    private int inspectionType;
    private Integer inspectedBy;
    private int inspectionTemplate;
    private LocalDate inspectionDate;
    private LocalTime inspectionTime;
    private String inspectionTypeJson;
    private InspectionResult result;
    private String summary;



}
