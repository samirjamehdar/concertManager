package com.test;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class testCustomerCrud {
    @Test
    public void testAddingCustomers() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    }
}
