package com.dima.querydslIntegration;

import com.dima.entity.ActiveSubstance;
import com.dima.entity.Product;
import com.dima.util.TestBase;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.jupiter.api.Test;

import static com.dima.entity.QActiveSubstance.activeSubstance;
import static com.dima.entity.QManufacturer.manufacturer;
import static com.dima.entity.QProduct.product;
import static com.dima.entity.QProductActiveSubstance.productActiveSubstance;
import static com.dima.entity.QProductCategory.productCategory;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductIT extends TestBase {

    @Test
    void findAllProducts() {
        session.beginTransaction();
        var result = new JPAQuery<Product>(session)
                .select(product)
                .from(product)
                .fetch();

        assertThat(result).hasSize(5);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).containsExactlyInAnyOrder("Boldenone", "Aspirin", "Testosterone", "Vitamin complex", "Vitamin C");
    }

    @Test
    void findAllProductsByName() {
        session.beginTransaction();
        var result = new JPAQuery<Product>(session)
                .select(product)
                .from(product)
                .where(product.name.eq("Aspirin"))
                .fetch();

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getName)
                .findFirst();

        assertThat(actualResult.get()).isEqualTo("Aspirin");
    }

    @Test
    void findAllProductsByManufacturer() {
        session.beginTransaction();
        var result = new JPAQuery<Product>(session)
                .select(product)
                .from(product)
                .join(product.manufacturer, manufacturer)
                .where(manufacturer.name.eq("Pharmacom"))
                .fetch();

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Testosterone", "Boldenone");
    }

    @Test
    void findAllProductsByCategory() {
        session.beginTransaction();
        var result = new JPAQuery<Product>(session)
                .select(product)
                .from(productCategory)
                .join(productCategory.products, product)
                .where(productCategory.name.eq("Vitamins"))
                .fetch();

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Vitamin complex", "Vitamin C");
    }

    @Test
    void findProductsByActiveSubstance() {
        session.beginTransaction();

        var result = new JPAQuery<Product>(session)
                .select(product)
                .from(product)
                .join(product.productActiveSubstances, productActiveSubstance)
                .join(productActiveSubstance.activeSubstance, activeSubstance)
                .where(activeSubstance.name.eq("Ascorbic acid"))
                .fetch();

        assertThat(result).hasSize(3);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Aspirin", "Vitamin complex", "Vitamin C");
    }
    @Test
    void findByActiveSubstanceByProduct() {
        session.beginTransaction();

        var result = new JPAQuery<ActiveSubstance>(session)
                .select(activeSubstance)
                .from(activeSubstance)
                .join(activeSubstance.productActiveSubstances, productActiveSubstance)
                .join(productActiveSubstance.product, product)
                .where(product.name.eq("Aspirin"))
                .fetch();

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(ActiveSubstance::getName)
                .toList();

        assertThat(actualResult).contains("Acetylsalicylic acid", "Ascorbic acid");
    }

    @Test
    void findProductsWherePriceBetween() {
        session.beginTransaction();

        var result = new JPAQuery<Product>(session)
                .select(product)
                .from(product)
                .where(product.price.between(30, 300))
                .orderBy(product.price.asc())
                .fetch();

        assertThat(result).hasSize(3);

        var actualResult = result.stream()
                .map(Product::getPrice)
                .toList();

        assertThat(actualResult).contains(45.00F, 125.00F, 56.20F);
        assertThat(actualResult.get(2)).isEqualTo(125.00F);
    }
}
