package com.prodnees.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin {
    @Id
    private int userId;

    public int getUserId() {
        return userId;
    }

    public Admin setUserId(int userId) {
        this.userId = userId;
        return this;
    }
}
