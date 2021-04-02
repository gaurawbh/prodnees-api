package com.prodnees.dto.user;


import javax.validation.constraints.NotBlank;

public class UserAttributesDto {
    @NotBlank(message = "firsName cannot be blank")
    private String firstName;
    @NotBlank(message = "lastName cannot be blank")
    private String lastName;
    private String phoneNumber;
    private String address;

    public String getFirstName() {
        return firstName;
    }

    public UserAttributesDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserAttributesDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserAttributesDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserAttributesDto setAddress(String address) {
        this.address = address;
        return this;
    }
}
