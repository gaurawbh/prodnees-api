package com.prodnees.domain.rels;

import com.prodnees.domain.rels.id.AssociatesId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(AssociatesId.class)
public class Associates implements Serializable {
    @Id
    private int adminId;
    private String adminEmail;
    @Id
    private int associateId;
    private String associateEmail;

    public int getAdminId() {
        return adminId;
    }

    public Associates setAdminId(int adminId) {
        this.adminId = adminId;
        return this;
    }

    public int getAssociateId() {
        return associateId;
    }

    public Associates setAssociateId(int userId) {
        this.associateId = userId;
        return this;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public Associates setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
        return this;
    }

    public String getAssociateEmail() {
        return associateEmail;
    }

    public Associates setAssociateEmail(String associateEmail) {
        this.associateEmail = associateEmail;
        return this;
    }
}
