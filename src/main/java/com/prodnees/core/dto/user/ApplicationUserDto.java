package com.prodnees.core.dto.user;

import com.prodnees.auth.domain.ApplicationRole;
import com.prodnees.core.config.constants.LocalConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ApplicationUserDto {
    @NotBlank(message = "firstName cannot be null or blank")
    private String firstName;
    @NotBlank(message = "lastName cannot be null or blank")
    private String lastName;
    @NotBlank(message = "email cannot be null or blank")
    @Email(regexp = LocalConstants.EMAIL_REGEX, message = "invalid email format")
    private String email;
    private ApplicationRole role;

    public String getFirstName() {
        return firstName;
    }

    public ApplicationUserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ApplicationUserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ApplicationUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public ApplicationRole getRole() {
        return role;
    }

    public ApplicationUserDto setRole(ApplicationRole role) {
        this.role = role;
        return this;
    }
}
