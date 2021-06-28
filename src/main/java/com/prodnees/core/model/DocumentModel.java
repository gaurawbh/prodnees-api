package com.prodnees.core.model;

import com.prodnees.core.domain.doc.DocumentPermission;

public class DocumentModel {

    private int id;
    private String name;
    private String description;
    private String contentType;
    private String documentUrl;
    private String documentDownloadUrl;
    private DocumentPermission documentPermission;

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

    public String getDescription() {
        return description;
    }

    public DocumentModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public DocumentModel setContentType(String contentType) {
        this.contentType = contentType;
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


    public DocumentPermission getDocumentPermission() {
        return documentPermission;
    }

    public DocumentModel setDocumentPermission(DocumentPermission documentPermission) {
        this.documentPermission = documentPermission;
        return this;
    }
}
