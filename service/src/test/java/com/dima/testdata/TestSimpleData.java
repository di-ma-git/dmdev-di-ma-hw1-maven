package com.dima.testdata;

import com.dima.entity.ActiveSubstance;
import com.dima.entity.Manufacturer;
import com.dima.entity.Order;
import com.dima.entity.Product;
import com.dima.entity.ProductCategory;
import com.dima.entity.User;
import com.dima.enums.OrderStatus;
import com.dima.enums.Role;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class TestSimpleData {

    public User getSimpleTestUser() {
        return User.builder()
                .name("someuser2")
                .email("test42@gmail.com")
                .password("12345")
                .phoneNumber("79870003211")
                .role(Role.CUSTOMER)
                .build();
    }

    public Order getSimpleTestOrder() {
        return Order.builder()
                .orderStatus(OrderStatus.NOT_PAID)
                .orderDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();
    }

    public ProductCategory getSimpleTestProductCategory() {
        return ProductCategory.builder()
                .name("Antidepressants")
                .description("Some description")
                .build();
    }

    public Product getSimpleTestProduct() {
        return Product.builder()
                .name("Aspirine")
                .price(20.33F)
                .quantityPerPackaging(60)
                .quantityPerDose(300D)
                .description("Some description of product")
                .build();
    }

    public Manufacturer getSimpleTestManufacturer() {
        return Manufacturer.builder()
                .name("Novartis")
                .country("USA")
                .description("Some description of manufacturer")
                .build();
    }

    public ActiveSubstance getSimpleTestActiveSubstance(String name) {
        return ActiveSubstance.builder()
                .name(name)
                .description("Some description")
                .build();
    }
}
