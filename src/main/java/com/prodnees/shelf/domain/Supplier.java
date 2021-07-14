/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.shelf.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "name is a required field and must not be blank")
    private String name;
    private String address;
    private String contactPerson;
    private String email;
    private String phoneNumber;
    private String description;

    public int getId() {
        return id;
    }

    public Supplier setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Supplier setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Supplier setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public Supplier setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Supplier setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Supplier setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Supplier setDescription(String description) {
        this.description = description;
        return this;
    }
}
