package util;

import com.dmdev.dima.entity.util.HibernateUtil;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

@UtilityClass
public class HibernateTestUtil {

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15.3");


    static {
        container.start();
    }

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = HibernateUtil.buildConfiguration();
        configuration.setProperty("hibernate.connection.url", container.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", container.getUsername());
        configuration.setProperty("hibernate.connection.password", container.getPassword());
        configuration.configure();

        return configuration.buildSessionFactory();

    }

}
