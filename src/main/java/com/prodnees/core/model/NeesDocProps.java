package com.prodnees.core.model;

public enum NeesDocProps {
    id,
    number,
    name,
    description,
    docType,
    docSubType,
    objectType,
    objectId,
    createdBy,
    lastModifiedBy,
    //Files will not be permanently deleted. Will not be visible to the User if marked as deleted
    deleted,
    createdDatetime,
    lastModifiedDatetime,
    mimeContentType,
    docUrl,
    docDownloadUrl,
    permission;

    public enum DoctypeProps{
        id,
        name,
        description,
        subTypes,
        active,
        sys
    }
}
