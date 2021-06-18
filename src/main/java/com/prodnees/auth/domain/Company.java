/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.auth.domain;

import com.prodnees.auth.config.CurrencyCode;
import org.apache.tomcat.util.buf.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.prodnees.auth.config.Table.Catalog.AUTH_TABLE;


@Entity
@Table(catalog = AUTH_TABLE)
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "name cannot be null or blank")
    private String name;
    @Size(min = 10, message = "schemaInstance must be at least 10 characters long")
    private String schemaInstance;
    private String companyPan;
    @CurrencyCode(message = "Invalid currency code")
    private String currency;
    private String country;
    private String zoneId;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean active;
    private LocalDate lastActive;



    public int getId() {
        return id;
    }

    public Company setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return sanitiseCompanyName(name);
    }

    public Company setName(String name) {
        this.name = sanitiseCompanyName(name);
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Company setAddress(String address) {
        this.address = address;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Company setActive(boolean active) {
        this.active = active;
        return this;
    }

    public String getCompanyPan() {
        return companyPan;
    }

    public Company setCompanyPan(String companyPan) {
        this.companyPan = companyPan;
        return this;
    }

    String sanitiseCompanyName(String name) {
        String[] nameList = name.split(" ");
        List<String> sanitisedString = new ArrayList<>();
        for (String st : nameList) {
            if (!st.isBlank()) {
                sanitisedString.add(st.trim());
            }
        }
        return StringUtils.join(sanitisedString, ' ');

    }


    public String getCountry() {
        return country;
    }

    public Company setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getZoneId() {
        return zoneId;
    }

    public Company setZoneId(String zoneId) {
        this.zoneId = zoneId;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Company setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Company setEmail(String email) {
        this.email = email;
        return this;
    }

    public LocalDate getLastActive() {
        return lastActive;
    }

    public Company setLastActive(LocalDate lastActive) {
        this.lastActive = lastActive;
        return this;
    }

    public String getSchemaInstance() {
        return schemaInstance;
    }

    public Company setSchemaInstance(String schemaInstance) {
        this.schemaInstance = schemaInstance;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public Company setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
}
