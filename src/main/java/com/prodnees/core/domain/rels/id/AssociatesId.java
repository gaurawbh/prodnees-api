package com.prodnees.core.domain.rels.id;

import java.io.Serializable;

public class AssociatesId implements Serializable {
    private int adminId;
    private int associateId;

    public int getAdminId() {
        return adminId;
    }

    public AssociatesId setAdminId(int adminId) {
        this.adminId = adminId;
        return this;
    }

    public int getAssociateId() {
        return associateId;
    }

    public AssociatesId setAssociateId(int associateId) {
        this.associateId = associateId;
        return this;
    }
}
