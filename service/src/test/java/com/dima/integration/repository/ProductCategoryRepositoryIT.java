package com.dima.integration.repository;

import com.dima.entity.Product;
import com.dima.repository.ProductCategoryRepository;
import com.dima.repository.ProductRepository;
import com.dima.testdata.TestSimpleData;
import com.dima.util.TestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

//@RequiredArgsConstructor
public class ProductCategoryRepositoryIT extends TestBase {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductCategoryRepositoryIT(ProductCategoryRepository productCategoryRepository, ProductRepository productRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
    }

    @Test
    void saveProductCategorySuccessful() {
        var productCategory = TestSimpleData.getSimpleTestProductCategory();

        productCategoryRepository.save(productCategory);
        entityManager.flush();
        entityManager.clear();

        assertThat(productCategory.getId()).isNotNull();
    }

    @Test
    void findProductCategorySuccessful() {
        var productCategory = TestSimpleData.getSimpleTestProductCategory();
        var product = TestSimpleData.getSimpleTestProduct();
        productCategory.addProduct(product);

        productCategoryRepository.save(productCategory);
        productRepository.save(product);
        entityManager.flush();
        entityManager.clear();

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
        entityManager.flush();
        entityManager.clear();
        productCategory.setDescription("Another description");
        productCategoryRepository.update(productCategory);
        entityManager.flush();
        entityManager.clear();

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
        entityManager.flush();
        entityManager.clear();
        product.setProductCategory(null);
        entityManager.merge(product);
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
        entityManager.flush();
        entityManager.clear();

        var product2 = Product.builder()
                .name("Vitamin A")
                .price(123.33F)
                .quantityPerPackaging(20L)
                .quantityPerDose(0.3534D)
                .description("Some description of product2")
                .productCategory(productCategory)
                .manufacturer(TestSimpleData.getSimpleTestManufacturer())
                .build();
        productCategoryRepository.findById(productCategory.getId());
        productRepository.save(product2);
        entityManager.merge(productCategory);
        entityManager.clear();

        var actualResult = productCategoryRepository.findById(productCategory.getId());

        assertThat(actualResult.get().getProducts()).hasSize(2);
    }
}
