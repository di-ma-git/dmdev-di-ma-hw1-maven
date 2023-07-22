package com.dima.integration;

import com.dima.entity.Product;
import com.dima.entity.ProductCategory;
import org.junit.jupiter.api.Test;
import com.dima.util.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductCategoryIT extends TestBase {

    @Test
    void saveProductCategorySuccessful() {
        var productCategory = testSimpleData.getSimpleTestProductCategory();
        var product = testSimpleData.getSimpleTestProduct();

        session.beginTransaction();
        session.save(productCategory);
        session.save(product);
        session.flush();
        session.clear();

        assertThat(productCategory.getId()).isNotNull();
    }
    @Test
    void getProductCategorySuccessful() {
        var productCategory = testSimpleData.getSimpleTestProductCategory();
        var product = testSimpleData.getSimpleTestProduct();
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
        var productCategory = testSimpleData.getSimpleTestProductCategory();
        var product = testSimpleData.getSimpleTestProduct();
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
        var productCategory = testSimpleData.getSimpleTestProductCategory();
        var product = testSimpleData.getSimpleTestProduct();
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
        var productCategory = testSimpleData.getSimpleTestProductCategory();
        var product1 = testSimpleData.getSimpleTestProduct();
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
                .manufacturer(testSimpleData.getSimpleTestManufacturer())
                .build();
        session.get(ProductCategory.class, productCategory.getId());
        session.save(product2);
        session.refresh(productCategory);
        session.clear();

        var actualResult = session.get(ProductCategory.class, productCategory.getId());

        assertThat(actualResult.getProducts()).hasSize(2);
    }
}
