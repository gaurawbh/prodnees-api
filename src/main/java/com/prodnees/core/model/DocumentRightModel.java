package com.prodnees.core.model;

import com.prodnees.core.domain.enums.ObjectRight;

public class DocumentRightModel {
    private int userId;
    private DocumentModel documentModel;
    private ObjectRight objectRight;

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

    public ObjectRight getObjectRightType() {
        return objectRight;
    }

    public DocumentRightModel setObjectRightType(ObjectRight objectRight) {
        this.objectRight = objectRight;
        return this;
    }
}
