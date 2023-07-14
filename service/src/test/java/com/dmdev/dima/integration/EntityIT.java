package com.dmdev.dima.integration;

import com.dmdev.dima.entity.ActiveSubstance;
import com.dmdev.dima.entity.Manufacturer;
import com.dmdev.dima.entity.Order;
import com.dmdev.dima.entity.Payment;
import com.dmdev.dima.entity.PermissionRole;
import com.dmdev.dima.entity.Product;
import com.dmdev.dima.entity.ProductCategory;
import com.dmdev.dima.entity.User;
import com.dmdev.dima.entity.enums.Role;
import com.dmdev.dima.entity.enums.Status;
import org.assertj.core.api.Assertions;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.*;

public class EntityIT {

    private Configuration configuration;

    @BeforeEach
    void init() {
        configuration = new Configuration();
        configuration.configure();
    }

    @Test
    void saveAndGetUserSuccess() {
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            var user = User.builder()
                    .userName("someuser2")
                    .userEmail("test2@gmail.com")
                    .userPassword("12345")
                    .userPhoneNumber("+79876543211")
                    .build();

            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();

            assertThat(user.getUserId()).isNotNull();

            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Test
    void saveAndGetProductCategorySuccess() {
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            var productCategory = ProductCategory.builder()
                    .name("Painkillers")
                    .description("Some description of painkillers")
                    .build();

            session.beginTransaction();
            session.save(productCategory);
            session.getTransaction().commit();

            assertThat(productCategory.getId()).isNotNull();

            session.beginTransaction();
            session.delete(productCategory);
            session.getTransaction().commit();
        }
    }

    @Test
    void saveAndGetProductSuccess() {
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            var product = Product.builder()
                    .name("Aspirine")
                    .price(20.33F)
                    .quantityPerPackaging(60L)
                    .quantityPerDose(300D)
                    .description("Some description of product")
                    .build();

            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();

            assertThat(product.getId()).isNotNull();

            session.beginTransaction();
            session.delete(product);
            session.getTransaction().commit();
        }
    }

    @Test
    void saveAndGetPermissionRoleSuccess() {
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            var role = new PermissionRole(1, Role.ADMIN, "Administrator");

            session.beginTransaction();
            session.save(role);
            session.getTransaction().commit();

            assertThat(role.getId()).isNotNull();

            session.beginTransaction();
            session.delete(role);
            session.getTransaction().commit();
        }
    }

    @Test
    void saveAndGetPaymentSuccess() {
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            var payment = Payment.builder()
                    .id(1)
                    .paymentType("Cache")
                    .build();

            session.beginTransaction();
            session.save(payment);
            session.getTransaction().commit();

            assertThat(payment.getId()).isNotNull();

            session.beginTransaction();
            session.delete(payment);
            session.getTransaction().commit();
        }
    }

    @Test
    void saveAndGetOrderSuccess() {
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            var order = Order.builder()
                    .customerId(1L)
                    .status(Status.NOT_PAID)
                    .orderDate(LocalDateTime.of(2023, Month.APRIL, 3, 12, 00, 12 ))
                    .build();

            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();

            assertThat(order.getId()).isNotNull();

            session.beginTransaction();
            session.delete(order);
            session.getTransaction().commit();
        }
    }

    @Test
    void saveAndGetManufacturerSuccess() {
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            var manufacture = Manufacturer.builder()
                    .name("Pfizer")
                    .country("USA")
                    .description("Some description of manufacturer")
                    .build();

            session.beginTransaction();
            session.save(manufacture);
            session.getTransaction().commit();

            assertThat(manufacture.getId()).isNotNull();

            session.beginTransaction();
            session.delete(manufacture);
            session.getTransaction().commit();
        }
    }

    @Test
    void saveAndGetActiveSubstanceSuccess() {
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            var substance = ActiveSubstance.builder()
                    .name("Fluoxetine")
                    .description("Some description of substance")
                    .build();

            session.beginTransaction();
            session.save(substance);
            session.getTransaction().commit();

            assertThat(substance.getId()).isNotNull();

            session.beginTransaction();
            session.delete(substance);
            session.getTransaction().commit();
        }
    }



}
