package com.prodnees.auth;

public interface AuthAction {

    boolean authenticateWithForgotPasswordCredentials(String email, String password);

    void processForgotPassword(String email);
}
