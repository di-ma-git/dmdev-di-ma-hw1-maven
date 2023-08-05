package com.dima.util;

import com.dima.testData.TestDataImport;
import com.dima.testData.TestSimpleData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Proxy;

public class TestBaseEntityManager {

    private static SessionFactory sessionFactory;
    public static Session session;
//    public static TestSimpleData TestSimpleData;

    @BeforeAll
    static void init() {
        sessionFactory = HibernateUtil.buildSessionFactory();
//        sessionFactory = HibernateTestUtil.buildSessionFactory();
        session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
    }

    @AfterAll
    static void close() {
        sessionFactory.close();
    }

    @BeforeEach
    void createSession() {
        session.beginTransaction();
        TestDataImport.importData(session);
        session.flush();
        session.clear();
    }

    @AfterEach
    void closeSession() {
        session.getTransaction().rollback();
        session.close();
    }
}
