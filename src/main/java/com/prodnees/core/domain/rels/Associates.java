package com.prodnees.core.domain.rels;

import com.prodnees.core.domain.rels.id.AssociatesId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@IdClass(AssociatesId.class)
public class Associates implements Serializable {
    @Id
    private int adminId;
    private String adminEmail;
    @Id
    private int associateId;
    private String associateEmail;
    private LocalDate startedDate;

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

    public LocalDate getStartedDate() {
        return startedDate;
    }

    public Associates setStartedDate(LocalDate startedDate) {
        this.startedDate = startedDate;
        return this;
    }
}
