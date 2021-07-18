package com.prodnees.shelf.domain;

import com.prodnees.core.domain.user.NeesObject;
import com.prodnees.qc.domain.ValueType;

import javax.persistence.*;

@Entity
public class ObjectAttribute {
    @Id
    @GeneratedValue
    private int id;
    private String privateKey;
    @Enumerated(EnumType.STRING)
    private NeesObject neesObject;
    private String label;
    @Enumerated(EnumType.STRING)
    private ValueType valueType;
    private boolean required;
    private boolean sys;

    public int getId() {
        return id;
    }

    public ObjectAttribute setId(int id) {
        this.id = id;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public ObjectAttribute setLabel(String label) {
        this.label = label;
        return this;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public ObjectAttribute setValueType(ValueType valueType) {
        this.valueType = valueType;
        return this;
    }

    public boolean isRequired() {
        return required;
    }

    public ObjectAttribute setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public boolean isSys() {
        return sys;
    }

    public ObjectAttribute setSys(boolean sys) {
        this.sys = sys;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public ObjectAttribute setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public NeesObject getNeesObject() {
        return neesObject;
    }

    public ObjectAttribute setNeesObject(NeesObject neesObject) {
        this.neesObject = neesObject;
        return this;
    }
}
