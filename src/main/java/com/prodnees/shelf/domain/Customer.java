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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "name cannot be null or blank")
    private String name;
    private String address;
    private String contactPerson;
    private String email;
    private String phoneNumber;
    private String description;
    private String panId;
    private String argTwo;
    private String argThree;

    public int getId() {
        return id;
    }

    public Customer setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Customer setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public Customer setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Customer setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Customer setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Customer setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPanId() {
        return panId;
    }

    public Customer setPanId(String argOne) {
        this.panId = argOne;
        return this;
    }

    public String getArgTwo() {
        return argTwo;
    }

    public Customer setArgTwo(String argTwo) {
        this.argTwo = argTwo;
        return this;
    }

    public String getArgThree() {
        return argThree;
    }

    public Customer setArgThree(String argThree) {
        this.argThree = argThree;
        return this;
    }
}
