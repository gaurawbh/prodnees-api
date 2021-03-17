package com.prodnees.domain.rels;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(DocumentManager.class)
public class DocumentManager implements Serializable {
    @Id
    private int documentId;
    @Id
    private int manageId;

    public int getDocumentId() {
        return documentId;
    }

    public DocumentManager setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public int getManageId() {
        return manageId;
    }

    public DocumentManager setManageId(int manageId) {
        this.manageId = manageId;
        return this;
    }
}
