package com.prodnees.shelf.domain;

public enum ProductGroupEnum {

    rawMaterial(
            "Raw Material",
            "Group of products used in the primary production or manufacturing of goods but are not tested against the requirements",
            true),
    quarantineStock(
            "Quarantine Stock",
            "Group of products that failed the requirements. May need to be returned to the supplier",
            true),
    productionStock(
            "Production Stock",
            "Group of products that passed the requirements and ready to be used in production or manufacturing of goods",
            true),
    salesStock(
            "Sales Stock",
            "Group of products that are for sale",
            true);

    private final String label;
    private final String description;
    private final boolean active;

    ProductGroupEnum(String label, String description, boolean active) {
        this.label = label;
        this.description = description;
        this.active = active;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }
}
