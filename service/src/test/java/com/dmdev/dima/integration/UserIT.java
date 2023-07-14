package com.dmdev.dima.integration;

import com.dmdev.dima.entity.Order;
import com.dmdev.dima.entity.User;
import com.dmdev.dima.entity.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import util.TestBase;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class UserIT extends TestBase {

    @Test
    void saveUserSuccessfulTest() {
        var user = testData.getSimpleTestUser();
        var order = testData.getSimpleTestOrder();
        user.addOrder(order);

        session.beginTransaction();
        session.save(user);

        assertThat(user.getId()).isNotNull();

    }

    @Test
    void getUserSuccessfulTest() {
        var user = testData.getSimpleTestUser();
        var order = testData.getSimpleTestOrder();
        user.addOrder(order);

        session.beginTransaction();
        session.save(user);
        session.flush();
        session.clear();

        var actualResult = session.get(User.class, user.getId());

        assertThat(actualResult).isEqualTo(user);
    }

    @Test
    void updateUserSuccessfulTest() {
        var user = testData.getSimpleTestUser();
        var order = testData.getSimpleTestOrder();
        user.addOrder(order);

        session.beginTransaction();
        session.save(user);
        user.setPassword("54321");
        session.flush();
        session.clear();

        var actualResult = session.get(User.class, user.getId());

        assertThat(actualResult.getPassword()).isEqualTo("54321");
    }

    @Test
    void deleteUserSuccessfulTest() {
        var user = testData.getSimpleTestUser();
        var order = testData.getSimpleTestOrder();
        user.addOrder(order);

        session.beginTransaction();
        session.save(user);
        session.flush();
        session.clear();
        session.remove(user);

        var actualResult = session.get(User.class, user.getId());

        assertThat(actualResult).isNull();
    }

    @Test
    void getUserOrdersTest() {
        var user = testData.getSimpleTestUser();
        var order1 = Order.builder()
                .orderStatus(OrderStatus.NOT_PAID)
                .orderDate(LocalDateTime.now())
                .user(user)
                .build();
        var order2 = Order.builder()
                .orderStatus(OrderStatus.DELIVERED)
                .orderDate(LocalDateTime.now())
                .user(user)
                .build();

        session.beginTransaction();
        session.save(user);
        session.save(order1);
        session.save(order2);
        session.flush();
        session.clear();

        var actualResult = session.get(User.class, user.getId());

        assertThat(actualResult.getOrders()).hasSize(2);
    }

    @Test
    void saveUserCascadeOrdersTest() {
        var user = testData.getSimpleTestUser();
        var order = testData.getSimpleTestOrder();
        user.addOrder(order);

        session.beginTransaction();
        session.save(user);
        session.flush();
        session.clear();

        var actualResult = session.get(Order.class, order.getId());

        assertThat(actualResult).isNotNull();
    }

    @Test
    void deleteUserCascadeOrdersTest() {
        var user = testData.getSimpleTestUser();
        var order = testData.getSimpleTestOrder();
        user.addOrder(order);

        session.beginTransaction();
        session.save(user);
        session.flush();
        session.clear();
        var delUser = session.get(User.class, user.getId());
        session.delete(delUser);
        session.flush();
        session.clear();

        var actualResult = session.get(Order.class, order.getId());

        assertThat(actualResult).isNull();
    }

    @Test
    void orphanRemovalDeleteTest() {
        var user = testData.getSimpleTestUser();
        var order1 = Order.builder()
                .orderStatus(OrderStatus.NOT_PAID)
                .orderDate(LocalDateTime.now())
                .build();
        var order2 = Order.builder()
                .orderStatus(OrderStatus.DELIVERED)
                .orderDate(LocalDateTime.now())
                .build();
        user.addOrder(order1);
        user.addOrder(order2);

        session.beginTransaction();
        session.save(user);
        user.getOrders().removeIf(order -> order.getOrderStatus().equals(OrderStatus.NOT_PAID));

        var actualResult = session.get(User.class, user.getId());

        assertThat(actualResult.getOrders()).hasSize(1);
    }
}
