package com.prodnees.config.constants;

public enum APIErrors {


    SUCCESS(0, "Success"),
    USER_NOT_FOUND(5, "User Not Found"),
    INVALID_ROLE(7, "missing or invalid role"),
    EMAIL_TAKEN(8, "email already taken"),
    INVALID_SUPER_EMAIL_FORMAT(10, "Invalid email format for a super user"),
    INVALID_EMAIL_FORMAT(12, "invalid email format"),
    PHONE_NUMBER_TAKEN(14, "phone number already taken"),
    INVALID_PHONE_NUMBER_FORMAT(15, "invalid phone number format"),
    INVALID_PASSWORD(16, "password is a required field & password must be at least 6 characters long"),
    INVALID_USER_REGISTRATION_MODEL(19, "required properties missing, firstName, lastName, email, designationId, role are required properties"),
    ACCESS_DENIED(21, "you do not have access to this resource"),
    USER_NOT_ENABLED(23, "user not enabled"),
    EMAIL_NOT_FOUND(24, "email not found: %s"),
    OTP_NOT_FOUND(25, "otp not found"),
    INVALID_USER_ROLE(26, "invalid user role"),
    INVALID_JWT_TOKEN(30, "invalid or missing token"),
    PROTECTED_URL(31, "attempt to access the protected url"),
    TEMP_PASSWORD_UNCHANGED(32, "user cannot access the app without changing the temp password, /secure/user/change-temp-password"),
    INVALID_REQUEST_BODY(42, "invalid request body"),
    OBJECT_NOT_FOUND(42, "object does not exist"),
    DUPLICATE_DATA(44, "duplicate row for object %s"),
    INVALID_REQUEST_PARAM(45, "Invalid request param");

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

