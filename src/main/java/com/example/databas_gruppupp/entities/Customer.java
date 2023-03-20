package com.example.databas_gruppupp.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "kunder")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int customerId;

    @Column(name = "förnamn", nullable = false, length = 45)
    private String first_name;

    @Column(name = "efternamn", nullable = false, length = 45)
    private String lastName;

    @Column(name = "födelsedatum", nullable = false)
    private LocalDate birthDate;

    @Column(name = "telefonnummer", nullable = false)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private User user;

    @ManyToOne
    private WC wc;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "kund_konserter",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "concert_id"))
    private List<Concert> concerts = new ArrayList<>();

    public Customer() {
    }

    public Customer(String first_name, String lastName, LocalDate birthDate, String phoneNumber, Address address, User user) {
        this.first_name = first_name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.user = user;
        this.birthDate = birthDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WC getWc() {
        return wc;
    }

    public void setWc(WC wc) {
        this.wc = wc;
    }

    public void addConcert(Concert concert) {
        this.concerts.add(concert);
        concert.getCustomers().add(this);
    }

    public void removeConcert(Concert concert) {
        this.concerts.remove(concert);
        concert.getCustomers().remove(this);
    }

    public List<Concert> getConcerts() {
        return concerts;
    }

    public void setConcerts(List<Concert> concerts) {
        this.concerts = concerts;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", first_name='" + first_name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                ", user=" + user +
                '}';
    }
}