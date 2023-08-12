package com.dima.config;

import com.dima.entity.ActiveSubstance;
import com.dima.entity.Manufacturer;
import com.dima.entity.Order;
import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance;
import com.dima.entity.ProductCategory;
import com.dima.entity.ProductInOrder;
import com.dima.entity.User;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

@Configuration
@Import(ApplicationConfiguration.class)
public class ApplicationTestConfiguration {

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15.3");

    @Bean
    public SessionFactory sessionFactory() {
        var configuration = new org.hibernate.cfg.Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Product.class);
        configuration.addAnnotatedClass(Manufacturer.class);
        configuration.addAnnotatedClass(ActiveSubstance.class);
        configuration.addAnnotatedClass(ProductCategory.class);
        configuration.addAnnotatedClass(Order.class);
        configuration.addAnnotatedClass(ProductInOrder.class);
        configuration.addAnnotatedClass(ProductActiveSubstance.class);
        configuration.setProperty("hibernate.connection.url", container.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", container.getUsername());
        configuration.setProperty("hibernate.connection.password", container.getPassword());
        configuration.configure();

        return configuration.buildSessionFactory();
    }

    @PostConstruct
    void init() {
        container.start();
    }

}
