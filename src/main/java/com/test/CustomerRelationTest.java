package com.test;

import com.example.databas_gruppupp.entities.Customer;
import com.example.databas_gruppupp.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;


import static org.junit.Assert.*;

public class CustomerRelationTest {
    /* Innan testerna körs bör alla tabeller tas bort och dummy data läggas till */

    @Test
    public void testCustomerUserRelationDelete() {
        /* Testa om en User försvinner om vi tar bort en Customer som är kopplad till User:n */
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Customer testCustomer = session.get(Customer.class, 1);
        session.remove(testCustomer);
        User testUser = session.get(User.class, 1);
        assertNull(testUser);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testCustomerUserRelationDeleteUser() {
        /* Testa om en Customer försvinner om vi tar bort en User som är kopplad till Customer:n */
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User testUser = session.get(User.class, 2);
        session.remove(testUser);
        Customer testCustomer = session.get(Customer.class, 2);
        assertNull(testCustomer);
        session.getTransaction().commit();
        session.close();
    }



}
