package com.example.databas_gruppupp.entities;

import jakarta.persistence.*;

@Entity
public class Arena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int arenaId;

    @Column(name = "namn", length = 45, nullable = false)
    private String name;

    @Column(name = "inomhus/utomhus", nullable = false)
    private String venueInOut;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "addres_id", nullable = false)
    private Address address;

    public Arena() {
    }

    public Arena(String name, Address address, String venueInOut) {
        this.name = name;
        this.address = address;
        this.venueInOut = venueInOut;
    }

    public int getArenaId() {
        return arenaId;
    }

    public void setArenaId(int arenaId) {
        this.arenaId = arenaId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenueInOut() {
        return venueInOut;
    }

    public void setVenueInOut(java.lang.String toString) {
        this.venueInOut = venueInOut;
    }

    @Override
    public java.lang.String toString() {
        return name + ", " + " arenan är placerad: " + venueInOut;
    }
}