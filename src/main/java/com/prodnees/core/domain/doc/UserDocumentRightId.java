package com.prodnees.core.domain.doc;

import java.io.Serializable;

public class UserDocumentRightId implements Serializable {
    private int documentId;
    private int userId;

    public UserDocumentRightId() {
    }

    public UserDocumentRightId(int documentId, int userId) {
        this.documentId = documentId;
        this.userId = userId;
    }

    public int getDocumentId() {
        return documentId;
    }

    public UserDocumentRightId setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public UserDocumentRightId setUserId(int userId) {
        this.userId = userId;
        return this;
    }
}
