package com.prodnees.qc.domain;

public enum ValueType {
    Number("Number", java.lang.Number.class),
    String("Text", java.lang.String.class),
    Boolean("Yes/No", java.lang.Boolean.class);
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
