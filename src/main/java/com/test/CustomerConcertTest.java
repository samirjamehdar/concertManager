package com.test;

import com.example.databas_gruppupp.entities.Concert;
import com.example.databas_gruppupp.entities.Customer;
import com.example.databas_gruppupp.entities.CustomerConcert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import static org.junit.Assert.*;

public class CustomerConcertTest {
    @Test
    public void testDeleteConcertsFromCustomer() {
        /* Testing if concerts are deleted from Concerts table if they are deleted from a Customer
            which they should not be.
        */
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Customer testCustomer = session.get(Customer.class, 1);
        Concert testConcert = session.get(Concert.class, 1);
        testCustomer.removeConcert(testConcert);
        session.update(testCustomer);
        CustomerConcert testCustomerConcert = session.get(CustomerConcert.class, 1);
        assertNull(testCustomerConcert);
        session.getTransaction().commit();
        session.close();
    }
}
