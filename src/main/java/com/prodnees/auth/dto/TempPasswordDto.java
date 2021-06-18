package com.prodnees.auth.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TempPasswordDto {
    @NotBlank(message = "password cannot be null or blank")
    @Size(min = 6, message = "password must be at least 6 characters long")
    private String password;
    @NotBlank(message = "repeatPassword cannot be null or blank")
    @Size(min = 6, message = "repeatPassword must be at least 6 characters long")
    private String repeatPassword;

    public String getPassword() {
        return password;
    }

    public TempPasswordDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public TempPasswordDto setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
        return this;
    }
}