package com.dima.util;

import com.dima.testdata.TestDataImport;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@IT
public class TestBase {

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15.3");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
        registry.add("logging.level.root", () -> "DEBUG");
    }

    @Autowired
    protected EntityManager entityManager;

    @BeforeAll
    static void init() {
        container.start();
    }


    @BeforeEach
    void createSession() {
        TestDataImport.importData(entityManager);
        entityManager.flush();
        entityManager.clear();
    }
}
