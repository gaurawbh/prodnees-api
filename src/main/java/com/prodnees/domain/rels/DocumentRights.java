package com.prodnees.domain.rels;


import com.prodnees.domain.rels.id.DocumentRightsId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(DocumentRightsId.class)
public class DocumentRights implements Serializable {
    @Id
    private int userId;
    @Id
    private int documentId;
    private ObjectRightsType objectRightsType;

    public DocumentRights() {
    }

    public DocumentRights(int userId, int documentId, ObjectRightsType objectRightsType) {
        this.userId = userId;
        this.documentId = documentId;
        this.objectRightsType = objectRightsType;
    }

    public int getUserId() {
        return userId;
    }

    public DocumentRights setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getDocumentId() {
        return documentId;
    }

    public DocumentRights setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public ObjectRightsType getDocumentRightsType() {
        return objectRightsType;
    }

    public DocumentRights setDocumentRightsType(ObjectRightsType objectRightsType) {
        this.objectRightsType = objectRightsType;
        return this;
    }
}
