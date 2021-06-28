package com.prodnees.core.domain.doc;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(UserDocumentRightId.class)
public class UserDocumentRight implements Serializable {
    @Id
    private int userId;
    @Id
    private int documentId;
    private DocumentPermission documentPermission;


    public int getUserId() {
        return userId;
    }

    public UserDocumentRight setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getDocumentId() {
        return documentId;
    }

    public UserDocumentRight setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }


    public DocumentPermission getDocumentPermission() {
        return documentPermission;
    }

    public UserDocumentRight setDocumentPermission(DocumentPermission documentPermission) {
        this.documentPermission = documentPermission;
        return this;
    }
}
