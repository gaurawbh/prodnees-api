package com.prodnees.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ApprovalDocument {

    @Id
    @GeneratedValue
    private int id;
    private int documentId;
    private DocumentState documentState;

    public int getId() {
        return id;
    }

    public ApprovalDocument setId(int id) {
        this.id = id;
        return this;
    }

    public int getDocumentId() {
        return documentId;
    }

    public ApprovalDocument setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public DocumentState getDocumentState() {
        return documentState;
    }

    public ApprovalDocument setDocumentState(DocumentState documentState) {
        this.documentState = documentState;
        return this;
    }
}
