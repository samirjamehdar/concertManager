package com.example.databas_gruppupp.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class WC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "namn", length = 45, nullable = false)
    private String name;

    @OneToMany(targetEntity = Concert.class, mappedBy = "wc", cascade = CascadeType.ALL)
    private List<Concert> concertList;

    @OneToMany(targetEntity = Customer.class, mappedBy = "wc", cascade = CascadeType.ALL)
    private List<Customer> customerList;

    public WC() {
    }

    public WC(String name, List<Concert> concertList, List<Customer> customerList) {
        this.name = name;
        this.concertList = concertList;
        this.customerList = customerList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Concert> getConcertList() {
        return concertList;
    }

    public void setConcertList(List<Concert> concertList) {
        this.concertList = concertList;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    @Override
    public String toString() {
        return "WC{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", concertList=" + concertList +
                ", customerList=" + customerList +
                '}';
    }
}