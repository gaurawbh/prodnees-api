package com.prodnees.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prodnees.config.constants.DateTimeFormats;
import org.springframework.http.MediaType;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * A document that can be referenced in other Objects or ApprovalDocument
 * <P>{@link com.prodnees.domain.batch.BatchApprovalDocument}</P>
 * <P>{@link com.prodnees.domain.stage.StageApprovalDocument}</P>
 */
@Entity
public class Document {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
    private String contentType;
    @JsonFormat(pattern = DateTimeFormats.DATE_TIME)
    private LocalDateTime createdDatetime;
    private byte[] file;

    public int getId() {
        return id;
    }

    public Document setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Document setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Document setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public Document setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public LocalDateTime getCreatedDatetime() {
        return createdDatetime;
    }

    public Document setCreatedDatetime(LocalDateTime createdDatetime) {
        this.createdDatetime = createdDatetime;
        return this;
    }

    public byte[] getFile() {
        return file;
    }

    public Document setFile(byte[] file) {
        this.file = file;
        return this;
    }
}
