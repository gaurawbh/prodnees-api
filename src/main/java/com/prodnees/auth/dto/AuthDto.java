package com.prodnees.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AuthDto {
    private static final String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    @NotBlank(message = "email cannot be null or blank")
    @Email(regexp = regex)
    private String email;
    private String password;

    public AuthDto() {
    }

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
