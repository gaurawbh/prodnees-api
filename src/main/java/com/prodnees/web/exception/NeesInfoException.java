package com.prodnees.web.exception;

public class NeesInfoException extends RuntimeException {
    private final String message;

    public NeesInfoException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
