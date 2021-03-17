package com.prodnees.web.exception;

public class NeesInfoException extends RuntimeException {
    private String message;

    public int getCode() {
        return code;
    }

    public NeesInfoException setCode(int code) {
        this.code = code;
        return this;
    }

    private int code;

    public NeesInfoException(String message) {
        this.message = message;
        this.code = 999;
    }

    public NeesInfoException() {
    }

    @Override
    public String getMessage() {
        return message;
    }

    public NeesInfoException setMessage(String message) {
        this.message = message;
        return this;
    }
}
