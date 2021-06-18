/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.auth.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class JwtTail {
    @Id
    private int userId;
    private String email;
    private String tail;
    private LocalDate lastChangedDate;

    public int getUserId() {
        return userId;
    }

    public JwtTail setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public JwtTail setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTail() {
        return tail;
    }

    public JwtTail setTail(String tail) {
        this.tail = tail;
        return this;
    }

    public LocalDate getLastChangedDate() {
        return lastChangedDate;
    }

    public JwtTail setLastChangedDate(LocalDate lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
        return this;
    }
}
