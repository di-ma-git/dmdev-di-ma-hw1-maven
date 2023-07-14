package com.dmdev.dima.integration;

import com.dmdev.dima.entity.ActiveSubstance;
import com.dmdev.dima.entity.Product;
import com.dmdev.dima.entity.ProductActiveSubstance;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import testData.TestData;
import util.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductActiveSubstanceIT extends TestBase {

    @Test
    void createProductActiveSubstanceSuccessfulTest() {
        var activeSubstance = testData.getSimpleTestActiveSubstance();
        var product = testData.getSimpleTestProduct();
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
    void getProductActiveSubstanceSuccessfulTest() {
        var activeSubstance = testData.getSimpleTestActiveSubstance();
        var product = testData.getSimpleTestProduct();
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
    void getActiveSubstanceByProductSuccessfulTest() {
        var activeSubstance = testData.getSimpleTestActiveSubstance();
        var product = testData.getSimpleTestProduct();
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
