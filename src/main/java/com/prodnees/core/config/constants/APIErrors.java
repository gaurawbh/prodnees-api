package com.prodnees.core.config.constants;

public enum APIErrors {


    SUCCESS(0, "Success"),
    USER_NOT_FOUND(5, "User Not Found"),
    EMAIL_TAKEN(8, "email already taken"),
    ACCESS_DENIED(21, "you do not have access to this resource"),
    UPDATE_DENIED(22, "you may not have enough permission to update / delete this resource"),
    USER_NOT_ENABLED(23, "user not enabled"),
    EMAIL_NOT_FOUND(24, "email not found: %s"),
    ASSOCIATES_ONLY(27, "Only associates can be given this Right"),
    INVALID_JWT_TOKEN(30, "invalid or missing token"),
    PROTECTED_URL(31, "attempt to access the protected url"),
    TEMP_PASSWORD_UNCHANGED(32, "user cannot access the app without changing the temp password, /secure/user/temp-password"),
    BATCH_NOT_FOUND(33, "batch not found"),
    INVALID_REQUEST_BODY(41, "invalid request body"),
    OBJECT_NOT_FOUND(42, "object does not exist");

    private final int code;
    private final String message;

    APIErrors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}

