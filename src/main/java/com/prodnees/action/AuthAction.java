package com.prodnees.action;

public interface AuthAction {

    boolean authenticateWithForgotPasswordCredentials(String email, String password);

    void processForgotPassword(String email);
}
