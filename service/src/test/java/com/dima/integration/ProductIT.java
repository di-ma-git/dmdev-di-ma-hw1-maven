package com.dima.integration;

import com.dima.entity.Product;
import com.dima.testData.TestSimpleData;
import org.junit.jupiter.api.Test;
import com.dima.util.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductIT extends TestBase {

    @Test
    void saveProductSuccessful() {
        var productCategory = TestSimpleData.getSimpleTestProductCategory();
        var manufacturer = TestSimpleData.getSimpleTestManufacturer();
        var activeSubstance = TestSimpleData.getSimpleTestActiveSubstance();
        var order = TestSimpleData.getSimpleTestOrder();
        var user = TestSimpleData.getSimpleTestUser();
        var product = Product.builder()
                .name("Aspirine")
                .price(20.33F)
                .quantityPerPackaging(60L)
                .quantityPerDose(300D)
                .description("Some description of product")
                .productCategory(productCategory)
                .manufacturer(manufacturer)
                .build();

        session.beginTransaction();
        session.save(productCategory);
        session.save(manufacturer);
        session.save(product);
        session.flush();
        session.clear();

        var actualResult = session.get(Product.class, product.getId());

        assertThat(actualResult.getId()).isNotNull();
    }

    @Test
    void getProductSuccessful() {
        var productCategory = TestSimpleData.getSimpleTestProductCategory();
        var manufacturer = TestSimpleData.getSimpleTestManufacturer();
        var product = Product.builder()
                .name("Aspirine")
                .price(20.33F)
                .quantityPerPackaging(60L)
                .quantityPerDose(300D)
                .description("Some description of product")
                .productCategory(productCategory)
                .manufacturer(manufacturer)
                .build();
        product.setProductCategory(productCategory);
        product.setManufacturer(manufacturer);
        productCategory.addProduct(product);

        session.beginTransaction();
        session.save(productCategory);
        session.save(manufacturer);
        session.save(product);
        session.flush();
        session.clear();

        var actualResult = session.get(Product.class, product.getId());

        assertThat(actualResult.getId()).isEqualTo(product.getId());
        assertThat(actualResult.getManufacturer()).isEqualTo(manufacturer);
        assertThat(actualResult.getProductCategory().getProducts().get(0)).isEqualTo(product);
    }

    @Test
    void updateProductSuccessful() {
        var productCategory = TestSimpleData.getSimpleTestProductCategory();
        var manufacturer = TestSimpleData.getSimpleTestManufacturer();
        var product = Product.builder()
                .name("Aspirine")
                .price(20.33F)
                .quantityPerPackaging(60L)
                .quantityPerDose(300D)
                .description("Some description of product")
                .productCategory(productCategory)
                .manufacturer(manufacturer)
                .build();
        product.setProductCategory(productCategory);
        product.setManufacturer(manufacturer);
        productCategory.addProduct(product);

        session.beginTransaction();
        session.save(productCategory);
        session.save(manufacturer);
        session.save(product);
        session.flush();
        session.clear();
        product.setPrice(25.33F);
        session.update(product);
        session.flush();
        session.clear();

        var actualResult = session.get(Product.class, product.getId());

        assertThat(actualResult.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    void deleteProductSuccessful() {
        var productCategory = TestSimpleData.getSimpleTestProductCategory();
        var manufacturer = TestSimpleData.getSimpleTestManufacturer();
        var product = Product.builder()
                .name("Aspirine")
                .price(20.33F)
                .quantityPerPackaging(60L)
                .quantityPerDose(300D)
                .description("Some description of product")
                .productCategory(productCategory)
                .manufacturer(manufacturer)
                .build();
        product.setProductCategory(productCategory);
        product.setManufacturer(manufacturer);
        productCategory.addProduct(product);

        session.beginTransaction();
        session.save(productCategory);
        session.save(manufacturer);
        session.save(product);
        session.delete(product);
        session.flush();
        session.clear();


        var actualResult = session.get(Product.class, product.getId());

        assertThat(actualResult).isNull();
    }
}
