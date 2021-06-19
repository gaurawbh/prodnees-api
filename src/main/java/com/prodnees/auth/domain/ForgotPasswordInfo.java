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
public class ForgotPasswordInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    private LocalDateTime createdDateTime;

    public int getId() {
        return id;
    }

    public ForgotPasswordInfo setId(int id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ForgotPasswordInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ForgotPasswordInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public ForgotPasswordInfo setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }
}
