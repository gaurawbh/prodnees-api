package com.prodnees.web.exception;


import com.prodnees.config.constants.APIErrors;

public class NeesBadRequestException extends RuntimeException {
    private String message;
    private int code;

    public NeesBadRequestException() {
    }

    public NeesBadRequestException(APIErrors constants) {
        this.message = constants.getMessage();
        this.code = constants.getCode();
    }

    public NeesBadRequestException(APIErrors constants, String arg) {

        this.message = String.format(constants.getMessage(), arg);
        this.code = constants.getCode();
    }

    public NeesBadRequestException(String message) {
        this.message = message;
        this.code = 99;
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
