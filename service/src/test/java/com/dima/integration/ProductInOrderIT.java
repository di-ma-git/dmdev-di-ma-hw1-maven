package com.dima.integration;

import com.dima.entity.Product;
import com.dima.entity.ProductInOrder;
import com.dima.entity.User;
import org.junit.jupiter.api.Test;
import com.dima.util.TestBase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductInOrderIT extends TestBase {

    @Test
    void createProductInOrderSuccessful() {
        var product = testSimpleData.getSimpleTestProduct();
        var user = testSimpleData.getSimpleTestUser();
        var order = testSimpleData.getSimpleTestOrder();
        var productInOrder = ProductInOrder.builder()
                .product(product)
                .order(order)
                .quantity(10)
                .build();
        order.setUser(user);
        order.addProductInOrder(productInOrder);

        session.beginTransaction();
        session.save(product);
        session.save(user);
        session.save(order);
        session.flush();
        session.clear();

        assertThat(productInOrder.getId()).isNotNull();
    }

    @Test
    void getProductInOrderSuccessful() {
        var product = testSimpleData.getSimpleTestProduct();
        var user = testSimpleData.getSimpleTestUser();
        var order = testSimpleData.getSimpleTestOrder();
        var productInOrder = ProductInOrder.builder()
                .product(product)
                .order(order)
                .quantity(10)
                .build();
        order.setUser(user);
        order.addProductInOrder(productInOrder);

        session.beginTransaction();
        session.save(product);
        session.save(user);
        session.save(order);
        session.flush();
        session.clear();

        var actualResult = session.get(ProductInOrder.class, productInOrder.getId());

        assertThat(actualResult).isEqualTo(productInOrder);
    }

    @Test
    void userShouldHave2ProductsInOrder() {
        var product1 = testSimpleData.getSimpleTestProduct();
        var product2 = Product.builder()
                .name("Vitamin C")
                .price(15.33F)
                .quantityPerPackaging(20L)
                .quantityPerDose(0.300D)
                .description("Some description of product2")
                .build();
        var user = testSimpleData.getSimpleTestUser();
        var order = testSimpleData.getSimpleTestOrder();
        var productInOrder1 = ProductInOrder.builder()
                .product(product1)
                .order(order)
                .quantity(10)
                .build();
        var productInOrder2 = ProductInOrder.builder()
                .product(product2)
                .order(order)
                .quantity(10)
                .build();
        order.setUser(user);
        order.addProductInOrder(productInOrder1);
        order.addProductInOrder(productInOrder2);

        session.beginTransaction();
        session.save(product1);
        session.save(product2);
        session.save(user);
        session.save(order);
        session.flush();
        session.clear();

        var actualResult = session.get(User.class, user.getId());
        List<String> products= actualResult.getOrders().get(0).getProductsInOrder().stream()
                .map(p -> p.getProduct().getName())
                .toList();

        assertThat(actualResult.getOrders().get(0).getProductsInOrder()).hasSize(2);
        assertThat(products).contains(product1.getName(), product2.getName());

    }
}
