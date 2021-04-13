package com.prodnees.domain.state;

import com.prodnees.domain.enums.StateStatus;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class StateReminder {
    @Id
    @GeneratedValue
    private int id;
    private int stateId;
    private StateStatus stateStatus; // on what state status, should this reminder be sent out
    private String recipients; // comma separated emails
    private String message;
    private String sender;
    private boolean sent;

    public int getId() {
        return id;
    }

    public StateReminder setId(int id) {
        this.id = id;
        return this;
    }

    public int getStateId() {
        return stateId;
    }

    public StateReminder setStateId(int stateId) {
        this.stateId = stateId;
        return this;
    }

    public StateStatus getStateStatus() {
        return stateStatus;
    }

    public StateReminder setStateStatus(StateStatus stateStatus) {
        this.stateStatus = stateStatus;
        return this;
    }

    public String getRecipients() {
        return recipients;
    }

    public StateReminder setRecipients(String recipients) {
        this.recipients = recipients;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public StateReminder setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getSender() {
        return sender;
    }

    public StateReminder setSender(String sender) {
        this.sender = sender;
        return this;
    }

    public boolean isSent() {
        return sent;
    }

    public StateReminder setSent(boolean sent) {
        this.sent = sent;
        return this;
    }
}
