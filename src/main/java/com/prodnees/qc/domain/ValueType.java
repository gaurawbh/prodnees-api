package com.prodnees.qc.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public enum ValueType {
    Number("Number", java.lang.Number.class),
    String("Text", java.lang.String.class),
    Boolean("Yes/No", java.lang.Boolean.class),
    DateTime("Date Time", LocalDateTime.class),
    Time("Time", LocalTime.class),
    PickList("Pick List", List.class),
    Date("Date", LocalDate.class);

    private final String label;
    private final Class<?> claz;

    ValueType(String label, Class<?> claz) {
        this.label = label;
        this.claz = claz;
    }

    public java.lang.String getLabel() {
        return label;
    }

    public Class<?> getClaz() {
        return claz;
    }
}
