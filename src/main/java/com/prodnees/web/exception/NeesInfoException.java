package com.prodnees.web.exception;

public class NeesInfoException extends RuntimeException {
    private final String message;

    public NeesInfoException(String message) {
        this.message = message;
        int code = 99;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
