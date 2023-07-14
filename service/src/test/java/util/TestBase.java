package util;

import com.dmdev.dima.entity.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import testData.TestData;
import util.HibernateTestUtil;

public class TestBase {

    static SessionFactory sessionFactory;
    public Session session;
    public TestData testData;

    @BeforeAll
    static void init() {
//        sessionFactory = HibernateTestUtil.buildSessionFactory();
        sessionFactory = HibernateUtil.buildSessionFactory();
    }

    @AfterAll
    static void close() {
        sessionFactory.close();
    }

    @BeforeEach
    void createSession() {
        session = sessionFactory.openSession();
        testData = new TestData();
    }

    @AfterEach
    void closeSession() {
        session.getTransaction().rollback();
        session.close();
    }
}
