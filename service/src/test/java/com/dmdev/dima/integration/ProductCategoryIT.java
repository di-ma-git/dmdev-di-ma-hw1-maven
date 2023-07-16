package com.dmdev.dima.integration;

import com.dmdev.dima.entity.Product;
import com.dmdev.dima.entity.ProductCategory;
import org.junit.jupiter.api.Test;
import util.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductCategoryIT extends TestBase {

    @Test
    void saveProductCategorySuccessful() {
        var productCategory = testData.getSimpleTestProductCategory();
        var product = testData.getSimpleTestProduct();

        session.beginTransaction();
        session.save(productCategory);
        session.save(product);
        session.flush();
        session.clear();

        assertThat(productCategory.getId()).isNotNull();
    }
    @Test
    void getProductCategorySuccessful() {
        var productCategory = testData.getSimpleTestProductCategory();
        var product = testData.getSimpleTestProduct();
        productCategory.addProduct(product);

        session.beginTransaction();
        session.save(productCategory);
        session.save(product);
        session.flush();
        session.clear();

        var actualResult = session.get(ProductCategory.class, productCategory.getId());

        assertThat(actualResult.getId()).isEqualTo(productCategory.getId());
        assertThat(actualResult.getProducts().get(0).getId()).isEqualTo(product.getId());
    }

    @Test
    void updateProductCategorySuccessful() {
        var productCategory = testData.getSimpleTestProductCategory();
        var product = testData.getSimpleTestProduct();
        productCategory.addProduct(product);

        session.beginTransaction();
        session.save(productCategory);
        session.save(product);
        session.flush();
        session.clear();
        productCategory.setDescription("Another description");
        session.update(productCategory);
        session.flush();
        session.clear();

        var actualResult = session.get(ProductCategory.class, productCategory.getId());

        assertThat(actualResult.getDescription()).isEqualTo("Another description");
    }

    @Test
    void deleteProductCategorySuccessful() {
        var productCategory = testData.getSimpleTestProductCategory();
        var product = testData.getSimpleTestProduct();
        productCategory.addProduct(product);

        session.beginTransaction();
        session.save(productCategory);
        session.save(product);
        session.flush();
        session.clear();
        productCategory.setDescription("Another description");
        session.remove(productCategory);

        var actualResult = session.get(ProductCategory.class, productCategory.getId());

        assertThat(actualResult).isNull();
    }

    @Test
    void addingProductToProductCategorySuccessful() {
        var productCategory = testData.getSimpleTestProductCategory();
        var product1 = testData.getSimpleTestProduct();
        productCategory.addProduct(product1);

        session.beginTransaction();
        session.save(productCategory);
        session.save(product1);
        session.flush();
        session.clear();

        var product2 = Product.builder()
                .name("Vitamine C")
                .price(123.33F)
                .quantityPerPackaging(20L)
                .quantityPerDose(0.3534D)
                .description("Some description of product2")
                .productCategory(productCategory)
                .manufacturer(testData.getSimpleTestManufacturer())
                .build();
        session.get(ProductCategory.class, productCategory.getId());
        session.save(product2);
        session.refresh(productCategory);
        session.clear();

        var actualResult = session.get(ProductCategory.class, productCategory.getId());

        assertThat(actualResult.getProducts()).hasSize(2);
    }
}
