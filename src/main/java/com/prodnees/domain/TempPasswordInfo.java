package com.prodnees.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class TempPasswordInfo {
    @Id
    @GeneratedValue
    private int id;
    private String email;
    private LocalDateTime createdDateTime;

    public int getId() {
        return id;
    }

    public TempPasswordInfo setId(int id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public TempPasswordInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public TempPasswordInfo setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }
}
