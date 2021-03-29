package com.prodnees.domain.rels.id;

import java.io.Serializable;

public class DocumentRightId implements Serializable {
    private int documentId;
    private int userId;

    public DocumentRightId() {
    }

    public DocumentRightId(int documentId, int userId) {
        this.documentId = documentId;
        this.userId = userId;
    }

    public int getDocumentId() {
        return documentId;
    }

    public DocumentRightId setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public DocumentRightId setUserId(int userId) {
        this.userId = userId;
        return this;
    }
}
