package com.dima.repositoryIntegration;

import com.dima.entity.Product;
import com.dima.repository.ProductCategoryRepository;
import com.dima.repository.ProductRepository;
import com.dima.testData.TestSimpleData;
import com.dima.util.TestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductCategoryRepositoryIT extends TestBase {

    private final ProductCategoryRepository productCategoryRepository = context.getBean(ProductCategoryRepository.class);
    private final ProductRepository productRepository = context.getBean(ProductRepository.class);

    @Test
    void saveProductCategorySuccessful() {
        var productCategory = TestSimpleData.getSimpleTestProductCategory();

        productCategoryRepository.save(productCategory);
        session.flush();
        session.clear();

        assertThat(productCategory.getId()).isNotNull();
    }

    @Test
    void findProductCategorySuccessful() {
        var productCategory = TestSimpleData.getSimpleTestProductCategory();
        var product = TestSimpleData.getSimpleTestProduct();
        productCategory.addProduct(product);

        productCategoryRepository.save(productCategory);
        productRepository.save(product);
        session.flush();
        session.clear();

        var actualResult = productCategoryRepository.findById(productCategory.getId());

        assertThat(actualResult).isPresent();
    }

    @Test
    void updateProductCategorySuccessful() {
        var productCategory = TestSimpleData.getSimpleTestProductCategory();
        var product = TestSimpleData.getSimpleTestProduct();
        productCategory.addProduct(product);

        productCategoryRepository.save(productCategory);
        productRepository.save(product);
        session.flush();
        session.clear();
        productCategory.setDescription("Another description");
        productCategoryRepository.update(productCategory);
        session.flush();
        session.clear();

        var actualResult = productCategoryRepository.findById(productCategory.getId());

        assertThat(actualResult.get().getDescription()).isEqualTo("Another description");
    }

    @Test
    void deleteProductCategorySuccessful() {
        var productCategory = TestSimpleData.getSimpleTestProductCategory();
        var product = TestSimpleData.getSimpleTestProduct();
        productCategory.addProduct(product);

        productCategoryRepository.save(productCategory);
        productRepository.save(product);
        session.flush();
        session.clear();
        product.setProductCategory(null);
        session.merge(product);
        productCategoryRepository.delete(productCategory);

        var actualResult = productCategoryRepository.findById(productCategory.getId());

        assertThat(actualResult).isEmpty();
    }

    @Test
    void addingProductToProductCategorySuccessful() {
        var productCategory = TestSimpleData.getSimpleTestProductCategory();
        var product1 = TestSimpleData.getSimpleTestProduct();
        productCategory.addProduct(product1);

        productCategoryRepository.save(productCategory);
        productRepository.save(product1);
        session.flush();
        session.clear();

        var product2 = Product.builder()
                .name("Vitamin C")
                .price(123.33F)
                .quantityPerPackaging(20L)
                .quantityPerDose(0.3534D)
                .description("Some description of product2")
                .productCategory(productCategory)
                .manufacturer(TestSimpleData.getSimpleTestManufacturer())
                .build();
        productCategoryRepository.findById(productCategory.getId());
        productRepository.save(product2);
        session.refresh(productCategory);
        session.clear();

        var actualResult = productCategoryRepository.findById(productCategory.getId());

        assertThat(actualResult.get().getProducts()).hasSize(2);
    }
}
