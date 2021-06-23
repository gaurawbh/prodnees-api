package com.prodnees.core.domain.rels;


import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.domain.rels.id.DocumentRightId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(DocumentRightId.class)
public class DocumentRight implements Serializable {
    @Id
    private int userId;
    @Id
    private int documentId;
    private ObjectRight objectRight;

    public DocumentRight() {
    }

    public DocumentRight(int userId, int documentId, ObjectRight objectRight) {
        this.userId = userId;
        this.documentId = documentId;
        this.objectRight = objectRight;
    }

    public int getUserId() {
        return userId;
    }

    public DocumentRight setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getDocumentId() {
        return documentId;
    }

    public DocumentRight setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public ObjectRight getDocumentRightsType() {
        return objectRight;
    }

    public DocumentRight setDocumentRightsType(ObjectRight objectRight) {
        this.objectRight = objectRight;
        return this;
    }
}
