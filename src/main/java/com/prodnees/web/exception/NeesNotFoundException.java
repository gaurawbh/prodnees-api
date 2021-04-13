package com.prodnees.web.exception;


import com.prodnees.config.constants.APIErrors;

public class NeesNotFoundException extends RuntimeException {

    private String message;
    private int code;

    public NeesNotFoundException() {

        this.message = APIErrors.OBJECT_NOT_FOUND.getMessage();
        this.code = APIErrors.OBJECT_NOT_FOUND.getCode();
    }

    public NeesNotFoundException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public NeesNotFoundException(APIErrors constants, String arg) {

        this.message = String.format(constants.getMessage(), arg);
        this.code = constants.getCode();
    }

    public NeesNotFoundException(APIErrors constants) {

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
