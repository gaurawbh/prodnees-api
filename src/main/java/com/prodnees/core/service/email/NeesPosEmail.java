package com.prodnees.core.service.email;

public class NeesPosEmail {
    private String from;
    private String to;
    private String subject;
    private String text;

    public NeesPosEmail() {
    }

    public NeesPosEmail(String from, String to, String subject, String text) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public NeesPosEmail setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public NeesPosEmail setTo(String to) {
        this.to = to;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public NeesPosEmail setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getText() {
        return text;
    }

    public NeesPosEmail setText(String text) {
        this.text = text;
        return this;
    }
}
