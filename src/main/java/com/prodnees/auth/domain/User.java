package com.prodnees.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.prodnees.auth.config.Table.Catalog.AUTH_TABLE;

/**
 * Application User Login Details
 */
@Entity
@Table(catalog = AUTH_TABLE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private ApplicationRole role;
    private boolean enabled;
    private int companyId;
    private String schemaInstance;
    private String firstName;
    private String lastName;

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

    public ApplicationRole getRole() {
        return role;
    }

    public User setRole(ApplicationRole role) {
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

    public int getCompanyId() {
        return companyId;
    }

    public User setCompanyId(int company_id) {
        this.companyId = company_id;
        return this;
    }

    public String getSchemaInstance() {
        return schemaInstance;
    }

    public User setSchemaInstance(String schema_instance) {
        this.schemaInstance = schema_instance;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String first_name) {
        this.firstName = first_name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String last_name) {
        this.lastName = last_name;
        return this;
    }

}
