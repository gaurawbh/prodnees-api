package com.prodnees.core.domain.user;

import com.prodnees.auth.domain.ApplicationRole;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class UserAttributes {
    @Id
    private int userId;
    @Enumerated(EnumType.STRING)
    private ApplicationRole role;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;

    public int getUserId() {
        return userId;
    }

    public UserAttributes setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserAttributes setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserAttributes setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserAttributes setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserAttributes setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserAttributes setAddress(String address) {
        this.address = address;
        return this;
    }

    public ApplicationRole getRole() {
        return role;
    }

    public UserAttributes setRole(ApplicationRole applicationRole) {
        this.role = applicationRole;
        return this;
    }
}
