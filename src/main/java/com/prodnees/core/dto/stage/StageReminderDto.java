package com.prodnees.core.dto.stage;

import com.prodnees.core.domain.enums.StageState;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class StageReminderDto {
    private int id;
    @Positive(message = "stateId must be a positive number")
    private int stageId;
    @NotNull(message = "stageState is a required field")
    private StageState stageState; // on what state status, should this reminder be sent out
    @NotEmpty(message = "recipientEmails must have at least one element")
    private String[] recipientEmails; // comma separated emails
    @NotEmpty(message = "message cannot be null or empty")
    private String message;

    public int getId() {
        return id;
    }

    public StageReminderDto setId(int id) {
        this.id = id;
        return this;
    }

    public int getStageId() {
        return stageId;
    }

    public StageReminderDto setStageId(int stageId) {
        this.stageId = stageId;
        return this;
    }

    public StageState getStageState() {
        return stageState;
    }

    public StageReminderDto setStageState(StageState stageState) {
        this.stageState = stageState;
        return this;
    }

    public String[] getRecipientEmails() {
        return recipientEmails;
    }

    public StageReminderDto setRecipientEmails(String[] recipientEmails) {
        this.recipientEmails = recipientEmails;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public StageReminderDto setMessage(String message) {
        this.message = message;
        return this;
    }
}
