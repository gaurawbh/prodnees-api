package com.prodnees.web.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prodnees.config.constants.APIErrors;
import java.time.LocalDateTime;

public class ErrorResponse {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime timestamp;
    private boolean error;
    private String message;
    private int messageCode;

    public ErrorResponse(String message, int messageCode) {
        this.messageCode = messageCode;
        this.timestamp = LocalDateTime.now();
        this.error = true;
        this.message = message;
    }

    public ErrorResponse(String message) {
        this.messageCode = 99;
        this.timestamp = LocalDateTime.now();
        this.error = true;
        this.message = message;
    }

    public ErrorResponse(APIErrors error) {
        this.messageCode = error.getCode();
        this.timestamp = LocalDateTime.now();
        this.error = true;
        this.message = error.getMessage();
    }

    public ErrorResponse() {
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ErrorResponse setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isError() {
        return error;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public ErrorResponse setMessageCode(int messageCode) {
        this.messageCode = messageCode;
        return this;
    }
}

