package com.prodnees.domain.rels;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(Associates.class)
public class Associates implements Serializable {
    @Id
    private int adminId;
    @Id
    private int userId;

    public int getAdminId() {
        return adminId;
    }

    public Associates setAdminId(int adminId) {
        this.adminId = adminId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Associates setUserId(int userId) {
        this.userId = userId;
        return this;
    }
}
