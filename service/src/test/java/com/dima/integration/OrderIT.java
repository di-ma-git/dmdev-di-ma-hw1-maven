package com.dima.integration;

import com.dima.entity.Order;
import com.dima.entity.Product;
import com.dima.entity.ProductInOrder;
import com.dima.entity.User;
import com.dima.enums.OrderStatus;
import com.dima.enums.Role;
import org.junit.jupiter.api.Test;
import com.dima.util.TestBase;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderIT extends TestBase {

    @Test
    void saveOrderSuccessful() {
        var user = User.builder()
                .name("someuser2")
                .email("test2@gmail.com")
                .password("12345")
                .phoneNumber("+79876543211")
                .role(Role.CUSTOMER)
                .build();
        var order = Order.builder()
                .orderStatus(OrderStatus.NOT_PAID)
                .orderDate(LocalDateTime.now())
                .user(user)
                .build();

        session.beginTransaction();
        session.save(user);
        session.save(order);
        session.flush();
        session.evict(order);

        assertThat(order.getId()).isNotNull();
    }

    @Test
    void getOrderSuccessful() {
        var user = User.builder()
                .name("someuser2")
                .email("test2@gmail.com")
                .password("12345")
                .phoneNumber("+79876543211")
                .role(Role.CUSTOMER)
                .build();
        var order = Order.builder()
                .orderStatus(OrderStatus.NOT_PAID)
                .orderDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user)
                .build();

        session.beginTransaction();
        session.save(user);
        session.save(order);
        session.flush();
        session.clear();

        var actualResult = session.get(Order.class, order.getId());

        assertThat(actualResult).isEqualTo(order);
    }

    @Test
    void updateOrderSuccessful() {
        var user = User.builder()
                .name("someuser2")
                .email("test2@gmail.com")
                .password("12345")
                .phoneNumber("+79876543211")
                .role(Role.CUSTOMER)
                .build();
        var order = Order.builder()
                .orderStatus(OrderStatus.NOT_PAID)
                .orderDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user)
                .build();

        session.beginTransaction();
        session.save(user);
        session.save(order);
        order.setOrderStatus(OrderStatus.PAID);
        session.flush();
        session.clear();

        var actualResult = session.get(Order.class, order.getId());

        assertThat(actualResult.getOrderStatus()).isEqualTo(OrderStatus.PAID);
    }

    @Test
    void deleteOrderButNotDeleteUserSuccessful() {
        var user = User.builder()
                .name("someuser2")
                .email("test2@gmail.com")
                .password("12345")
                .phoneNumber("+79876543211")
                .role(Role.CUSTOMER)
                .build();
        var order = Order.builder()
                .orderStatus(OrderStatus.NOT_PAID)
                .orderDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user)
                .build();

        session.beginTransaction();
        session.save(user);
        session.save(order);
        session.flush();
        session.clear();
        session.delete(order);
        session.flush();
        session.clear();

        var actualOrderResult = session.get(Order.class, order.getId());
        var actualUserResult = session.get(User.class, user.getId());

        assertThat(actualOrderResult).isNull();
        assertThat(actualUserResult).isNotNull();
        assertThat(actualUserResult.getOrders()).isEmpty();
    }

    @Test
    void addProductInOrder() {
        var user = User.builder()
                .name("someuser2")
                .email("test2@gmail.com")
                .password("12345")
                .phoneNumber("+79876543211")
                .role(Role.CUSTOMER)
                .build();
        var order = Order.builder()
                .orderStatus(OrderStatus.NOT_PAID)
                .orderDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user)
                .build();
        var product1 = Product.builder()
                .name("Aspirine")
                .price(20.33F)
                .quantityPerPackaging(60L)
                .quantityPerDose(300D)
                .description("Some description of product1")
                .build();
        var product2 = Product.builder()
                .name("Vitamine A")
                .price(50.00F)
                .quantityPerPackaging(60L)
                .quantityPerDose(0.333D)
                .description("Some description of product2")
                .build();
        var productInOrder1 = ProductInOrder.builder()
                .order(order)
                .product(product1)
                .quantity(25)
                .build();
        var productInOrder2 = ProductInOrder.builder()
                .order(order)
                .product(product2)
                .quantity(100)
                .build();

        session.beginTransaction();
        session.save(product1);
        session.save(product2);
        session.save(user);
        session.save(order);
        session.save(productInOrder1);
        session.save(productInOrder2);
        session.flush();
        session.clear();

        var productInOrderActRes1 = session.get(ProductInOrder.class, product1.getId());
        var productInOrderActRes2 = session.get(ProductInOrder.class, product2.getId());
        var actualResult = session.get(Order.class, order.getId());

        assertThat(actualResult.getProductsInOrder()).hasSize(2);
    }
}
