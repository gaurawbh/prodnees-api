package com.prodnees.dto.state;

import com.prodnees.domain.enums.StateStatus;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class StateReminderDto {
    private int id;
    @Positive(message = "stateId must be a positive number")
    private int stateId;
    @NotNull(message = "stateStatus is a required field")
    private StateStatus stateStatus; // on what state status, should this reminder be sent out
    @NotEmpty(message = "recipientEmails must have at least one element")
    private String[] recipientEmails; // comma separated emails
    @NotEmpty(message = "message cannot be null or empty")
    private String message;

    public int getId() {
        return id;
    }

    public StateReminderDto setId(int id) {
        this.id = id;
        return this;
    }

    public int getStateId() {
        return stateId;
    }

    public StateReminderDto setStateId(int stateId) {
        this.stateId = stateId;
        return this;
    }

    public StateStatus getStateStatus() {
        return stateStatus;
    }

    public StateReminderDto setStateStatus(StateStatus stateStatus) {
        this.stateStatus = stateStatus;
        return this;
    }

    public String[] getRecipientEmails() {
        return recipientEmails;
    }

    public StateReminderDto setRecipientEmails(String[] recipientEmails) {
        this.recipientEmails = recipientEmails;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public StateReminderDto setMessage(String message) {
        this.message = message;
        return this;
    }
}
