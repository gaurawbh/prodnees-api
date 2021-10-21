package com.prodnees.shelf.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

/**
 * Record of Products that belong to a Group
 */
@Entity
@IdClass(GroupProduct.class)
public class GroupProduct implements Serializable {
    @Id
    private int productId;
    @Id
    private int productGroupId;
    private int units;

    public int getProductId() {
        return productId;
    }

    public GroupProduct setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public int getProductGroupId() {
        return productGroupId;
    }

    public GroupProduct setProductGroupId(int productGroupId) {
        this.productGroupId = productGroupId;
        return this;
    }

    public int getUnits() {
        return units;
    }

    public GroupProduct setUnits(int units) {
        this.units = units;
        return this;
    }
}
