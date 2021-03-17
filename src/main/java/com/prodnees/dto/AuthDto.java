package com.prodnees.dto;

public class AuthDto {

    private String email;
    private String password;

    public AuthDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public AuthDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
