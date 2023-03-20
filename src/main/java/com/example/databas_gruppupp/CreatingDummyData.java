package com.example.databas_gruppupp;

import com.example.databas_gruppupp.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class CreatingDummyData {

    public static void main(String[] args) {

        addData();
    }

    public static void addData() {

        Address address = new Address("Riddarhustorget", "10", "11128", "STOCKHOLM");
        Address address1 = new Address("Kungsportsavenyn", "38", "41136", "GÖTEBORG");
        Address address2 = new Address("Ullevigatan", "4", "41140", "GÖTEBORG");
        Address address3 = new Address("Sätra Dalhallavägen", "201", "79591", "RÄTTVIK");
        Address address4 = new Address("Götagatan", "55", "11621", "STOCKHOLM");
        Address address5 = new Address("Skallarevägen", "42", "45291", "STRÖMSTAD");
        Address address6 = new Address("Nederby sjögärdet", "3", "96192", "BODEN");
        Address address7 = new Address("Synargatan", "2c", "81330", "HOFORS");
        Address address8 = new Address("Humlevägen", "25", "81630", "OCKELBO");
        Address address9 = new Address("Lokandervägen", "12A", "68150", "KRISTINEHAMN");

        Customer customer = new Customer("Anja", "Cederlund", LocalDate.of(1994, 02, 8),
                "0526-1150112", address5, new User("anjacederlund@hotmail.com", "anjapassword"));
        Customer customer1 = new Customer("Gun", "Persson", LocalDate.of(1971, 5, 31),
                "0921-3838174", address6, new User("gunpersson@hotmail.com", "gunpassword"));
        Customer customer2 = new Customer("Ralf", "Westin", LocalDate.of(2002, 9, 25),
                "019-9522345", address7, new User("ralfwestin@hotmail.com", "ralfpassword"));
        Customer customer3 = new Customer("Jon", "Hilmersson", LocalDate.of(1987, 5, 25),
                "0495-9760322", address8, new User("jonhilmersson@hotmail.com", "jonpassword"));
        Customer customer4 = new Customer("Hillbert", "Enqvist", LocalDate.of(1970, 10, 18),
                "026-3913430", address9, new User("hillbertenqvist@hotmail.com", "hillbertpassword"));

        Arena arena = new Arena("STOCKHOLM CHAMBER MUSIC", address, "INDOORS");
        Arena arena1 = new Arena("PARK LANE", address1, "INDOORS");
        Arena arena2 = new Arena("ULLEVI", address2, "OUTDOORS");
        Arena arena3 = new Arena("DALHALLA", address3, "INDOORS");
        Arena arena4 = new Arena("GÖTA LEJON", address4, "INDOORS");

        Concert concert = new Concert("WEBERKVARTETTEN", LocalDate.of(2022, 5, 21), 550.00, arena, null);
        Concert concert1 = new Concert("DIGGILOO", LocalDate.of(2023, 6, 15), 945.55, arena1, null);
        Concert concert2 = new Concert("HAIR - Musikalen", LocalDate.of(2023, 5, 12), 995.0, arena2, null);
        Concert concert3 = new Concert("ROCK THE DISCO", LocalDate.of(2023, 3, 25), 1095.00, arena3, 13);
        Concert concert4 = new Concert("METALLICA", LocalDate.of(2023, 6, 16), 1600.00, arena4, 16);

        customer1.addConcert(concert);
        customer1.addConcert(concert2);

        customer2.addConcert(concert3);
        customer2.addConcert(concert1);

        customer3.addConcert(concert4);
        customer3.addConcert(concert2);

        customer4.addConcert(concert3);
        customer4.addConcert(concert4);

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(address);
        session.persist(address1);
        session.persist(address2);
        session.persist(address3);
        session.persist(address4);
        session.persist(address5);
        session.persist(address6);
        session.persist(address7);
        session.persist(address8);
        session.persist(address9);
        session.persist(customer);
        session.persist(customer1);
        session.persist(customer2);
        session.persist(customer3);
        session.persist(customer4);
        session.persist(arena);
        session.persist(arena1);
        session.persist(arena2);
        session.persist(arena3);
        session.persist(arena4);
        session.persist(concert);
        session.persist(concert1);
        session.persist(concert2);
        session.persist(concert3);
        session.persist(concert4);

        session.getTransaction().commit();
        session.close();
    }
}