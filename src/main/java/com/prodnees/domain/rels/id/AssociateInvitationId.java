package com.prodnees.domain.rels.id;

import java.io.Serializable;

public class AssociateInvitationId implements Serializable {
    private int invitorId;
    private int inviteeId;

    public int getInvitorId() {
        return invitorId;
    }

    public AssociateInvitationId setInvitorId(int invitorId) {
        this.invitorId = invitorId;
        return this;
    }

    public int getInviteeId() {
        return inviteeId;
    }

    public AssociateInvitationId setInviteeId(int inviteeId) {
        this.inviteeId = inviteeId;
        return this;
    }
}
