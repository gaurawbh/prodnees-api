package com.prodnees.core.domain.doc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prodnees.core.config.constants.DateTimeFormats;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * A document that can be referenced in other Objects or ApprovalDocument
 * <P>{@link com.prodnees.core.domain.batch.BatchApprovalDocument}</P>
 * <P>{@link com.prodnees.core.domain.stage.StageApprovalDocument}</P>
 */
@Entity
public class NeesDoc {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
    private boolean deleted;
    private String contentType;
    @JsonFormat(pattern = DateTimeFormats.DATE_TIME)
    private LocalDateTime createdDatetime;
    private byte[] file;

    public int getId() {
        return id;
    }

    public NeesDoc setId(int id) {
        this.id = id;
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

    public String getContentType() {
        return contentType;
    }

    public NeesDoc setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public LocalDateTime getCreatedDatetime() {
        return createdDatetime;
    }

    public NeesDoc setCreatedDatetime(LocalDateTime createdDatetime) {
        this.createdDatetime = createdDatetime;
        return this;
    }

    public byte[] getFile() {
        return file;
    }

    public NeesDoc setFile(byte[] file) {
        this.file = file;
        return this;
    }
}
