package com.prodnees.auth.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Application User Login Details
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String email;
    private String password;
    private UserRole role;
    private boolean enabled;
    private int company_id;
    private String schema_instance;
    private String first_name;
    private String last_name;
    @Enumerated(EnumType.STRING)
    ApplicationRight application_right;

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserRole getRole() {
        return role;
    }

    public User setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public User setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public int getCompany_id() {
        return company_id;
    }

    public User setCompany_id(int company_id) {
        this.company_id = company_id;
        return this;
    }

    public String getSchema_instance() {
        return schema_instance;
    }

    public User setSchema_instance(String schema_instance) {
        this.schema_instance = schema_instance;
        return this;
    }

    public String getFirst_name() {
        return first_name;
    }

    public User setFirst_name(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public String getLast_name() {
        return last_name;
    }

    public User setLast_name(String last_name) {
        this.last_name = last_name;
        return this;
    }

    public ApplicationRight getApplication_right() {
        return application_right;
    }

    public User setApplication_right(ApplicationRight application_right) {
        this.application_right = application_right;
        return this;
    }
}
