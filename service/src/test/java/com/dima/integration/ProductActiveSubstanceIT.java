package com.dima.integration;

import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance;
import org.junit.jupiter.api.Test;
import com.dima.util.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductActiveSubstanceIT extends TestBase {

    @Test
    void createProductActiveSubstanceSuccessful() {
        var activeSubstance = testSimpleData.getSimpleTestActiveSubstance();
        var product = testSimpleData.getSimpleTestProduct();
        var productActiveSubstance = ProductActiveSubstance.builder()
                .build();
        productActiveSubstance.setActiveSubstance(activeSubstance);
        productActiveSubstance.setProduct(product);

        session.beginTransaction();
        session.save(activeSubstance);
        session.save(product);
        session.save(productActiveSubstance);
        session.flush();
        session.clear();

        assertThat(productActiveSubstance.getId()).isNotNull();
    }

    @Test
    void getProductActiveSubstanceSuccessful() {
        var activeSubstance = testSimpleData.getSimpleTestActiveSubstance();
        var product = testSimpleData.getSimpleTestProduct();
        var productActiveSubstance = ProductActiveSubstance.builder()
                .build();
        productActiveSubstance.setActiveSubstance(activeSubstance);
        productActiveSubstance.setProduct(product);

        session.beginTransaction();
        session.save(activeSubstance);
        session.save(product);
        session.save(productActiveSubstance);
        session.flush();
        session.clear();

        var actualResult = session.get(ProductActiveSubstance.class, productActiveSubstance.getId());

        assertThat(actualResult).isEqualTo(productActiveSubstance);
    }
    @Test
    void getActiveSubstanceByProductSuccessful() {
        var activeSubstance = testSimpleData.getSimpleTestActiveSubstance();
        var product = testSimpleData.getSimpleTestProduct();
        var productActiveSubstance = ProductActiveSubstance.builder()
                .build();
        productActiveSubstance.setActiveSubstance(activeSubstance);
        productActiveSubstance.setProduct(product);

        session.beginTransaction();
        session.save(activeSubstance);
        session.save(product);
        session.save(productActiveSubstance);
        session.flush();
        session.clear();

        var actualResult = session.get(Product.class, product.getId());

        assertThat(actualResult.getProductActiveSubstances().get(0).getActiveSubstance())
                .isEqualTo(activeSubstance);
    }
}