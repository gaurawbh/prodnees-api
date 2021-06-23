package com.prodnees.core.model.user;

public class AssociateModel {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private boolean isAssociate;

    public int getId() {
        return id;
    }

    public AssociateModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AssociateModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AssociateModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AssociateModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AssociateModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public AssociateModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public boolean isAssociate() {
        return isAssociate;
    }

    public AssociateModel setAssociate(boolean associate) {
        this.isAssociate = associate;
        return this;
    }
}
