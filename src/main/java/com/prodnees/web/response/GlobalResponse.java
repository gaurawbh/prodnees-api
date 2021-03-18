package com.prodnees.web.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalResponse {

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime timestamp;
    private HttpStatus status;
    private boolean error;
    private String message;
    private int messageCode;
    private List<?> objects;

    public GlobalResponse(HttpStatus status, boolean error, String message, int messageCode, List<?> objects) {
        this.error = error;
        this.messageCode = messageCode;
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.objects = objects;
    }

    public GlobalResponse(HttpStatus status, boolean error, String message, int messageCode, Object object) {
        this.messageCode = messageCode;

        List<Object> objects = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        String objectType = object.getClass().getSimpleName();
        map.put(objectType, object);
        objects.add(map);
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.error = error;
        this.objects = objects;
    }

    public GlobalResponse(HttpStatus status, boolean error, String message, int messageCode) {
        this.error = error;
        this.messageCode = messageCode;
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.objects = null;
    }

    public GlobalResponse() {
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public GlobalResponse setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public GlobalResponse setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public GlobalResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<?> getObjects() {
        return objects;
    }

    public GlobalResponse setObjects(List<Object> objects) {
        this.objects = objects;
        return this;
    }

    public boolean isError() {
        return error;
    }

    public GlobalResponse setError(boolean error) {
        this.error = error;
        return this;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public GlobalResponse setMessageCode(int messageCode) {
        this.messageCode = messageCode;
        return this;
    }
}

