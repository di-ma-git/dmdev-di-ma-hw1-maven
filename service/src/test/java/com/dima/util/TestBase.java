package com.dima.util;

import com.dima.config.ApplicationTestConfiguration;
import com.dima.testData.TestDataImport;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestBase {

    protected static AnnotationConfigApplicationContext context;

    public static Session session;

    @BeforeAll
    static void init() {
        context = new AnnotationConfigApplicationContext();
        context.register(ApplicationTestConfiguration.class);
        context.refresh();
        session = context.getBean(Session.class);
    }

    @AfterAll
    static void close() {
        context.close();
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
