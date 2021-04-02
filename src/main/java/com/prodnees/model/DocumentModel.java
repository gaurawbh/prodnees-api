package com.prodnees.model;

import com.prodnees.domain.enums.ObjectRightType;

public class DocumentModel {

    private int id;
    private String name;
    private String documentUrl;
    private String documentDownloadUrl;
    private ObjectRightType objectRightType;

    public int getId() {
        return id;
    }

    public DocumentModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DocumentModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public DocumentModel setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
        return this;
    }

    public String getDocumentDownloadUrl() {
        return documentDownloadUrl;
    }

    public DocumentModel setDocumentDownloadUrl(String documentDownloadUrl) {
        this.documentDownloadUrl = documentDownloadUrl;
        return this;
    }

    public ObjectRightType getObjectRightType() {
        return objectRightType;
    }

    public DocumentModel setObjectRightType(ObjectRightType objectRightType) {
        this.objectRightType = objectRightType;
        return this;
    }
}
