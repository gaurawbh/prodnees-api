package com.prodnees.core.domain.rels;

import com.prodnees.core.domain.enums.InvitationAction;
import com.prodnees.core.domain.rels.id.AssociateInvitationId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDate;

@Entity
@IdClass(AssociateInvitationId.class)
public class AssociateInvitation {

    @Id
    private int invitorId;
    private String invitorEmail;
    private String invitorComment;
    @Id
    private int inviteeId;
    private String inviteeEmail;
    private boolean accepted;
    private String inviteeComment;
    private InvitationAction action;
    private LocalDate invitationDate;

    public int getInvitorId() {
        return invitorId;
    }

    public AssociateInvitation setInvitorId(int invitorId) {
        this.invitorId = invitorId;
        return this;
    }

    public String getInvitorEmail() {
        return invitorEmail;
    }

    public AssociateInvitation setInvitorEmail(String invitorEmail) {
        this.invitorEmail = invitorEmail;
        return this;
    }

    public String getInvitorComment() {
        return invitorComment;
    }

    public AssociateInvitation setInvitorComment(String invitorComment) {
        this.invitorComment = invitorComment;
        return this;
    }

    public int getInviteeId() {
        return inviteeId;
    }

    public AssociateInvitation setInviteeId(int inviteeId) {
        this.inviteeId = inviteeId;
        return this;
    }

    public String getInviteeEmail() {
        return inviteeEmail;
    }

    public AssociateInvitation setInviteeEmail(String inviteeEmail) {
        this.inviteeEmail = inviteeEmail;
        return this;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public AssociateInvitation setAccepted(boolean accepted) {
        this.accepted = accepted;
        return this;
    }

    public String getInviteeComment() {
        return inviteeComment;
    }

    public AssociateInvitation setInviteeComment(String inviteeComment) {
        this.inviteeComment = inviteeComment;
        return this;
    }

    public InvitationAction getAction() {
        return action;
    }

    public AssociateInvitation setAction(InvitationAction action) {
        this.action = action;
        return this;
    }

    public LocalDate getInvitationDate() {
        return invitationDate;
    }

    public AssociateInvitation setInvitationDate(LocalDate invitationDate) {
        this.invitationDate = invitationDate;
        return this;
    }
}
