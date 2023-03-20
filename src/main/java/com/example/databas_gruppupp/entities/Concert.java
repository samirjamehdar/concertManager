package com.example.databas_gruppupp.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "konserter")
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int concertId;

    @Column(name = "artist_name", length = 50, nullable = false)
    private String artistName;

    @Column(name = "datum", nullable = false)
    private LocalDate date;

    @Column(name = "biljettpris", precision = 2, nullable = false)
    private double ticketPrice;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "arena_id", nullable = false)
    private Arena arena;

    @Column(name = "åldersgräns")
    private Integer ageRestriction;

    @ManyToOne
    private WC wc;

    @ManyToMany(mappedBy = "concerts")
    private List<Customer> customers = new ArrayList<>();

    public Concert() {
    }

    public Concert(String artistName, LocalDate date, double ticketPrice, Arena arena, Integer ageRestriction) {
        this.artistName = artistName;
        this.ticketPrice = ticketPrice;
        this.arena = arena;
        this.ageRestriction = ageRestriction;
        this.date = date;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public Integer getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(Integer ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public int getConcertId() {
        return concertId;
    }

    public void setConcertId(int concertId) {
        this.concertId = concertId;
    }

    public WC getWc() {
        return wc;
    }

    public void setWc(WC wc) {
        this.wc = wc;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
        customer.getConcerts().remove(this);
    }

    @Override
    public String toString() {
        return "Concert{" +
                "concertId=" + concertId +
                ", artistName='" + artistName + '\'' +
                ", date=" + date +
                ", ticketPrice=" + ticketPrice +
                ", arena=" + arena +
                ", ageRestriction=" + ageRestriction +
                '}';
    }
}