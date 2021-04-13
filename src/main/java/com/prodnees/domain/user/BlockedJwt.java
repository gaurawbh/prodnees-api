package com.prodnees.domain.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BlockedJwt {
    @Id
    @GeneratedValue
    private int id;
    private int userId;
    private String email;
    private String jwt;


    public int getId() {
        return id;
    }

    public BlockedJwt setId(int id) {
        this.id = id;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public BlockedJwt setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BlockedJwt setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getJwt() {
        return jwt;
    }

    public BlockedJwt setJwt(String jwt) {
        this.jwt = jwt;
        return this;
    }
}
