package com.prodnees.auth.dto;

import com.prodnees.core.config.constants.LocalConstants;
import com.prodnees.core.config.constraints.PhoneNumber;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SignupDto {

    @NotBlank(message = "firstName cannot be null or blank")
    private String firstName;
    @NotBlank(message = "lastName cannot be null or blank")
    private String lastName;
    @NotBlank(message = "email cannot be null or blank")
    @Email(regexp = LocalConstants.EMAIL_REGEX, message = "invalid email format")
    private String email;
    private String username;
    @PhoneNumber(message = "phoneNumber can only contain numbers and '+'")
    private String phoneNumber;
    //Domain info
    private String domainName;
    private String domainSuffix;
    @NotBlank(message = "companyName is a required field and cannot be blank")
    private String companyName;

    public String getFirstName() {
        return firstName;
    }

    public SignupDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public SignupDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public SignupDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public SignupDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public SignupDto setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SignupDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getDomainName() {
        return domainName;
    }

    public SignupDto setDomainName(String domainName) {
        this.domainName = domainName;
        return this;
    }

    public String getDomainSuffix() {
        return domainSuffix;
    }

    public SignupDto setDomainSuffix(String domainSuffix) {
        this.domainSuffix = domainSuffix;
        return this;
    }
}
