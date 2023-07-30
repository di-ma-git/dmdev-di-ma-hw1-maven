package com.dima.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import com.dima.testData.TestSimpleData;
import com.dima.testData.TestDataImport;

public class TestBase {

    static SessionFactory sessionFactory;
    public Session session;
    public TestSimpleData testSimpleData;

    @BeforeAll
    static void init() {
        sessionFactory = HibernateUtil.buildSessionFactory();
//        sessionFactory = HibernateTestUtil.buildSessionFactory();

    }

    @AfterAll
    static void close() {
        sessionFactory.close();
    }

    @BeforeEach
    void createSession() {
        testSimpleData = new TestSimpleData();
        session = sessionFactory.openSession();
        TestDataImport.importData(session);
        session.clear();

    }

    @AfterEach
    void closeSession() {
        session.getTransaction().rollback();
        session.close();
    }
}
