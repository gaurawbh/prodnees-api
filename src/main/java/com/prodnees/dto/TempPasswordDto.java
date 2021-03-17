package com.prodnees.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TempPasswordDto {
    @NotBlank
    @Size(min = 6)
    private String password;

    public String getPassword() {
        return password;
    }
}