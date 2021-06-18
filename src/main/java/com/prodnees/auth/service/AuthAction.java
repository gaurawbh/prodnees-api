package com.prodnees.auth.service;

public interface AuthAction {

    boolean authenticateWithForgotPasswordCredentials(String email, String password);

    void processForgotPassword(String email);
}
