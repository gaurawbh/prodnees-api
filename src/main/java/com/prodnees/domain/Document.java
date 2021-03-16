package com.prodnees.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * A document that can be referenced in other Objects or ApprovalDocument
 */
@Entity
public class Document {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private byte[] file;

    public int getId() {
        return id;
    }

    public Document setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Document setName(String name) {
        this.name = name;
        return this;
    }

    public byte[] getFile() {
        return file;
    }

    public Document setFile(byte[] file) {
        this.file = file;
        return this;
    }
}
