package com.prodnees.core.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AssociateInvitationDto {
    private static final String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    @NotBlank(message = "inviteeEmail cannot be null or blank")
    @Email(regexp = regex)
    private String inviteeEmail;
    @NotBlank(message = "inviteeFirstName cannot be null or blank, " +
            "Name will be used to create a new account if the Invitee does not have an account yet")
    private String inviteeFirstName;
    @NotBlank(message = "inviteeLastName cannot be null or blank," +
            "Name will be used to create a new account if the Invitee does not have an account yet")
    private String inviteeLastName;
    private String invitorComment;

    public String getInviteeEmail() {
        return inviteeEmail;
    }

    public AssociateInvitationDto setInviteeEmail(String inviteeEmail) {
        this.inviteeEmail = inviteeEmail;
        return this;
    }

    public String getInviteeFirstName() {
        return inviteeFirstName;
    }

    public AssociateInvitationDto setInviteeFirstName(String inviteeFirstName) {
        this.inviteeFirstName = inviteeFirstName;
        return this;
    }

    public String getInviteeLastName() {
        return inviteeLastName;
    }

    public AssociateInvitationDto setInviteeLastName(String inviteeLastName) {
        this.inviteeLastName = inviteeLastName;
        return this;
    }

    public String getInvitorComment() {
        return invitorComment;
    }

    public AssociateInvitationDto setInvitorComment(String invitorComment) {
        this.invitorComment = invitorComment;
        return this;
    }
}
