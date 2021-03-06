package com.prodnees.core.domain.doc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prodnees.core.config.constants.DateTimeFormats;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * A document that can be referenced in other Objects or ApprovalDocument
 */
@Entity
public class NeesDoc {

    @Id
    @GeneratedValue
    private int id;

    //Unique AlphaNumeric number generated by the System
    private String number;

    //File name but can be edited later
    private String name;

    private String description;
    private String docType;
    private String docSubType;
    private String mimeContentType;
    private String objectType;
    private Integer objectId;
    private Integer createdBy;
    private Integer lastModifiedBy;

    //Files will not be permanently deleted. Will not be visible to the User if marked as deleted
    private boolean deleted;
    @JsonFormat(pattern = DateTimeFormats.DATE_TIME)
    private LocalDateTime createdDatetime;
    @JsonFormat(pattern = DateTimeFormats.DATE_TIME)
    private LocalDateTime modifiedDatetime;

    public int getId() {
        return id;
    }

    public NeesDoc setId(int id) {
        this.id = id;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public NeesDoc setNumber(String docNumber) {
        this.number = docNumber;
        return this;
    }

    public String getName() {
        return name;
    }

    public NeesDoc setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public NeesDoc setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public NeesDoc setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public String getDocType() {
        return docType;
    }

    public NeesDoc setDocType(String docType) {
        this.docType = docType;
        return this;
    }

    public String getDocSubType() {
        return docSubType;
    }

    public NeesDoc setDocSubType(String docSubType) {
        this.docSubType = docSubType;
        return this;
    }

    public String getObjectType() {
        return objectType;
    }

    public NeesDoc setObjectType(String objectType) {
        this.objectType = objectType;
        return this;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public NeesDoc setObjectId(Integer objectId) {
        this.objectId = objectId;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public NeesDoc setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Integer getLastModifiedBy() {
        return lastModifiedBy;
    }

    public NeesDoc setLastModifiedBy(Integer lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public String getMimeContentType() {
        return mimeContentType;
    }

    public NeesDoc setMimeContentType(String contentType) {
        this.mimeContentType = contentType;
        return this;
    }

    public LocalDateTime getCreatedDatetime() {
        return createdDatetime;
    }

    public NeesDoc setCreatedDatetime(LocalDateTime createdDatetime) {
        this.createdDatetime = createdDatetime;
        return this;
    }

    public LocalDateTime getModifiedDatetime() {
        return modifiedDatetime;
    }

    public NeesDoc setModifiedDatetime(LocalDateTime modifiedDateTime) {
        this.modifiedDatetime = modifiedDateTime;
        return this;
    }
}
