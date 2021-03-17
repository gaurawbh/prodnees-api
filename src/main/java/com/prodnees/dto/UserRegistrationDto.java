package com.prodnees.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserRegistrationDto {

    private static final String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    @NotBlank(message = "firstName cannot be null or blank")
    private String firstName;
    @NotBlank(message = "lastName cannot be null or blank")
    private String lastName;
    @NotBlank(message = "email cannot be null or blank")
    @Email(regexp = regex)
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
