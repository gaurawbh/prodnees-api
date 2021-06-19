package com.prodnees.auth.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static com.prodnees.auth.config.Table.Catalog.AUTH_TABLE;

@Entity
@Table(catalog = AUTH_TABLE)
public class TempPasswordInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

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
