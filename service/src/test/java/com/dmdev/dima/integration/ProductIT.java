package com.dmdev.dima.integration;

import com.dmdev.dima.entity.ActiveSubstance;
import com.dmdev.dima.entity.Manufacturer;
import com.dmdev.dima.entity.Order;
import com.dmdev.dima.entity.Product;
import com.dmdev.dima.entity.ProductActiveSubstance;
import com.dmdev.dima.entity.ProductCategory;
import com.dmdev.dima.entity.User;
import org.junit.jupiter.api.Test;
import util.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductIT extends TestBase {

    @Test
    void saveProductSuccessfulTest() {
        var productCategory = testData.getSimpleTestProductCategory();
        var manufacturer = testData.getSimpleTestManufacturer();
        var activeSubstance = testData.getSimpleTestActiveSubstance();
        var order = testData.getSimpleTestOrder();
        var user = testData.getSimpleTestUser();
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
    void getProductSuccessfulTest() {
        var productCategory = testData.getSimpleTestProductCategory();
        var manufacturer = testData.getSimpleTestManufacturer();
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
    void updateProductSuccessfulTest() {
        var productCategory = testData.getSimpleTestProductCategory();
        var manufacturer = testData.getSimpleTestManufacturer();
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
    void deleteProductSuccessfulTest() {
        var productCategory = testData.getSimpleTestProductCategory();
        var manufacturer = testData.getSimpleTestManufacturer();
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
