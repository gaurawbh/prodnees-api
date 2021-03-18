package com.prodnees.domain.rels.id;

import java.io.Serializable;

public class DocumentRightsId implements Serializable {
    private int documentId;
    private int userId;

    public DocumentRightsId() {
    }

    public DocumentRightsId(int documentId, int userId) {
        this.documentId = documentId;
        this.userId = userId;
    }

    public int getDocumentId() {
        return documentId;
    }

    public DocumentRightsId setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public DocumentRightsId setUserId(int userId) {
        this.userId = userId;
        return this;
    }
}
