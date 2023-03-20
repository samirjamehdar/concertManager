package com.example.databas_gruppupp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "kund_konserter")
public class CustomerConcert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "concert_id")
    private Concert concert;

    public CustomerConcert() {
    }

    public CustomerConcert(Customer customer, Concert concert) {
        this.customer = customer;
        this.concert = concert;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Concert getConcert() {
        return concert;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }
}