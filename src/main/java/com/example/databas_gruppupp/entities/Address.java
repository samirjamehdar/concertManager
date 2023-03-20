package com.example.databas_gruppupp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "adress")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int addressId;

    @Column(name = "gata", length = 45, nullable = false)
    private String street;

    @Column(length = 6, nullable = false)
    private String houseNumber;

    @Column(length = 5, nullable = false)
    private String postalCode;

    @Column(length = 45, nullable = false)
    private String areaName;

    public Address() {
    }

    public Address(String street, String houseNumber, String postalCode, String areaName) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.areaName = areaName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return street + " " + houseNumber + ", " + postalCode + ", " + areaName;
    }
}
