package com.prodnees.domain.rels;

import java.io.Serializable;

public class DocumentRightsId implements Serializable {
    private int documentId;
    private int manageId;

    public int getDocumentId() {
        return documentId;
    }

    public DocumentRightsId setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public int getManageId() {
        return manageId;
    }

    public DocumentRightsId setManageId(int manageId) {
        this.manageId = manageId;
        return this;
    }
}
