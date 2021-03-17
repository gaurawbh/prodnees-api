package com.prodnees.web.exception;


import com.prodnees.config.constants.APIErrors;

public class NeesForbiddenException extends RuntimeException {
    private String message;
    private int code;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public NeesForbiddenException() {
    }

    public NeesForbiddenException(APIErrors constants) {
        this.message = constants.getMessage();
        this.code = constants.getCode();
    }

}
