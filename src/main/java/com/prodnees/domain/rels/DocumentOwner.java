package com.prodnees.domain.rels;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(DocumentOwner.class)
public class DocumentOwner implements Serializable {
    @Id
    private int ownerId;
    @Id
    private int documentId;

    public int getOwnerId() {
        return ownerId;
    }

    public DocumentOwner setOwnerId(int userId) {
        this.ownerId = userId;
        return this;
    }

    public int getDocumentId() {
        return documentId;
    }

    public DocumentOwner setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }
}
