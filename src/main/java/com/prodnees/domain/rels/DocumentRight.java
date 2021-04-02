package com.prodnees.domain.rels;


import com.prodnees.domain.enums.ObjectRightType;
import com.prodnees.domain.rels.id.DocumentRightId;
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
    private ObjectRightType objectRightType;

    public DocumentRight() {
    }

    public DocumentRight(int userId, int documentId, ObjectRightType objectRightType) {
        this.userId = userId;
        this.documentId = documentId;
        this.objectRightType = objectRightType;
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

    public ObjectRightType getDocumentRightsType() {
        return objectRightType;
    }

    public DocumentRight setDocumentRightsType(ObjectRightType objectRightType) {
        this.objectRightType = objectRightType;
        return this;
    }
}
