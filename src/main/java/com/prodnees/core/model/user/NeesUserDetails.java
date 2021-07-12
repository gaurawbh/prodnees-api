package com.prodnees.core.model.user;

import com.prodnees.auth.domain.ApplicationRole;

/**
 * Complete Details of a User.
 */
public class NeesUserDetails {
    private int id;
    private String email;
    private ApplicationRole role;
    private boolean enabled;
    private int companyId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;

    public int getId() {
        return id;
    }

    public NeesUserDetails setId(int id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public NeesUserDetails setEmail(String email) {
        this.email = email;
        return this;
    }

    public ApplicationRole getRole() {
        return role;
    }

    public int getCompanyId() {
        return companyId;
    }

    public NeesUserDetails setCompanyId(int companyId) {
        this.companyId = companyId;
        return this;
    }

    public NeesUserDetails setRole(ApplicationRole role) {
        this.role = role;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public NeesUserDetails setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public NeesUserDetails setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public NeesUserDetails setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public NeesUserDetails setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public NeesUserDetails setAddress(String address) {
        this.address = address;
        return this;
    }
}
