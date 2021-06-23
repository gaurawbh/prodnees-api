package com.prodnees.core.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AssociateInvitationActionDto {
    @NotBlank(message = "invitorEmail cannot be null or empty")
    private String invitorEmail;
    private String inviteeComment;
    @NotNull(message = "accept cannot be null")
    private boolean accept;

    public String getInvitorEmail() {
        return invitorEmail;
    }

    public AssociateInvitationActionDto setInvitorEmail(String invitorEmail) {
        this.invitorEmail = invitorEmail;
        return this;
    }

    public String getInviteeComment() {
        return inviteeComment;
    }

    public AssociateInvitationActionDto setInviteeComment(String inviteeComment) {
        this.inviteeComment = inviteeComment;
        return this;
    }

    public boolean isAccept() {
        return accept;
    }

    public AssociateInvitationActionDto setAccept(boolean accept) {
        this.accept = accept;
        return this;
    }
}
