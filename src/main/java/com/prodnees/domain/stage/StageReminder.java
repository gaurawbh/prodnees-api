package com.prodnees.domain.stage;

import com.prodnees.domain.enums.StageState;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Gives option on what {@link StageState} to send this reminder to the recipients
 */

@Entity
public class StageReminder {
    @Id
    @GeneratedValue
    private int id;
    private int stageId;
    private StageState stageState; // on what Stage state, should this reminder be sent out
    private String recipients; // comma separated emails
    private String message;
    private String sender;
    private boolean sent;

    public int getId() {
        return id;
    }

    public StageReminder setId(int id) {
        this.id = id;
        return this;
    }

    public int getStageId() {
        return stageId;
    }

    public StageReminder setStageId(int stateId) {
        this.stageId = stateId;
        return this;
    }

    public StageState getStateStatus() {
        return stageState;
    }

    public StageReminder setStateStatus(StageState stageState) {
        this.stageState = stageState;
        return this;
    }

    public String getRecipients() {
        return recipients;
    }

    public StageReminder setRecipients(String recipients) {
        this.recipients = recipients;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public StageReminder setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getSender() {
        return sender;
    }

    public StageReminder setSender(String sender) {
        this.sender = sender;
        return this;
    }

    public boolean isSent() {
        return sent;
    }

    public StageReminder setSent(boolean sent) {
        this.sent = sent;
        return this;
    }
}
