package com.prodnees.model;

import com.prodnees.domain.rels.ObjectRightType;

public class DocumentRightModel {
    private int userId;
    private DocumentModel documentModel;
    private ObjectRightType objectRightType;

    public int getUserId() {
        return userId;
    }

    public DocumentRightModel setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public DocumentRightModel setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
        return this;
    }

    public ObjectRightType getObjectRightType() {
        return objectRightType;
    }

    public DocumentRightModel setObjectRightType(ObjectRightType objectRightType) {
        this.objectRightType = objectRightType;
        return this;
    }
}
