package com.prodnees.domain.rels;


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
    private DocumentRightsType documentRightsType;

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

    public DocumentRightsType getDocumentRightsType() {
        return documentRightsType;
    }

    public DocumentRights setDocumentRightsType(DocumentRightsType documentRightsType) {
        this.documentRightsType = documentRightsType;
        return this;
    }
}
