package com.prodnees.model.user;

import com.prodnees.auth.domain.UserRole;

public class UserModel {
    private int id;
    private String email;
    private UserRole role;
    private boolean enabled;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;

    public int getId() {
        return id;
    }

    public UserModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserRole getRole() {
        return role;
    }

    public UserModel setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public UserModel setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserModel setAddress(String address) {
        this.address = address;
        return this;
    }
}
