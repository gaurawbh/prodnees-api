package com.prodnees.core.web.exception;


import com.prodnees.core.config.constants.APIErrors;

public class NeesForbiddenException extends RuntimeException {
    private String message;
    private int code;

    public NeesForbiddenException() {
    }

    public NeesForbiddenException(APIErrors constants) {
        this.message = constants.getMessage();
        this.code = constants.getCode();
    }

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

}
