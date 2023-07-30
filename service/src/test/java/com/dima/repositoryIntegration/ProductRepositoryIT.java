package com.dima.repositoryIntegration;

import com.dima.dto.ProductFilter;
import com.dima.entity.Product;
import com.dima.enums.MedicineType;
import com.dima.repository.ManufacturerRepository;
import com.dima.repository.ProductCategoryRepository;
import com.dima.repository.ProductRepository;
import com.dima.testData.TestSimpleData;
import com.dima.util.TestBaseEntityManager;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryIT extends TestBaseEntityManager {

    private final ProductRepository productRepository = new ProductRepository(session);
    private final ManufacturerRepository manufacturerRepository = new ManufacturerRepository(session);
    private final ProductCategoryRepository productCategoryRepository = new ProductCategoryRepository(session);

    @Test
    void saveProductSuccessful() {
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

        productCategoryRepository.save(productCategory);
        manufacturerRepository.save(manufacturer);
        productRepository.save(product);
        session.flush();
        session.clear();

        var actualResult = productRepository.findById(product.getId()).get();

        assertThat(actualResult.getId()).isNotNull();
        assertThat(actualResult.getManufacturer().getId()).isEqualTo(manufacturer.getId());
        assertThat(actualResult.getProductCategory().getId()).isEqualTo(productCategory.getId());
    }


    @Test
    void findProductByIdSuccessful() {
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

        productCategoryRepository.save(productCategory);
        manufacturerRepository.save(manufacturer);
        productRepository.save(product);
        session.flush();
        session.clear();

        var actualResult = productRepository.findById(product.getId()).get();

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

        productCategoryRepository.save(productCategory);
        manufacturerRepository.save(manufacturer);
        productRepository.save(product);
        session.flush();
        session.clear();
        product.setPrice(25.33F);
        session.update(product);
        session.flush();
        session.clear();

        var actualResult = productRepository.findById(product.getId()).get();

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

        productCategoryRepository.save(productCategory);
        manufacturerRepository.save(manufacturer);
        productRepository.save(product);
        session.flush();
        session.clear();
        session.delete(product);

        var actualResult = productRepository.findById(product.getId());

        assertThat(actualResult).isEmpty();
    }

    @Test
    void findAllProductsByName() {
        var result = productRepository.findAllProductsByName("Aspirin");

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getName)
                .findFirst();

        assertThat(actualResult.get()).contains("Aspirin");
    }

    @Test
    void findAllProductsByManufacturerName() {
        var result = productRepository.findByManufacturer("Pharmacom");

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Testosterone", "Boldenone");
    }

    @Test
    void findAllProductsByCategoryName() {
        var result = productRepository.findByCategory("Painkillers");

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Aspirin");
    }

    @Test
    void findAllProductsBySeveralMedicineType() {
        var result = productRepository.findByMedicineType(MedicineType.CAPSULES, MedicineType.INJECTIONS);

        assertThat(result).hasSize(6);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Aspirin");
    }

    @Test
    void findAllProductsByActiveSubstance() {
        var result = productRepository.findByActiveSubstance("Ascorbic acid");

        assertThat(result).hasSize(4);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Aspirin", "Vitamin complex", "Vitamin C");
    }

    @Test
    void findAllProductsByFilterWithMinAndMaxPrice() {

        var filter = ProductFilter.builder()
                .priceMin(30F)
                .priceMax(300F)
                .build();

        var result = productRepository.findByFilter(filter);

        assertThat(result).hasSize(3);

        var actualResult = result.stream()
                .map(Product::getPrice)
                .toList();

        assertThat(actualResult).contains(45.00F, 125.00F, 56.20F);
    }

    @Test
    void findAllProductsByFilterWithOnlyMaxPrice() {

        var filter = ProductFilter.builder()
                .priceMax(50F)
                .build();

        var result = productRepository.findByFilter(filter);

        assertThat(result).hasSize(4);

        var actualResult = result.stream()
                .map(Product::getPrice)
                .toList();

        assertThat(actualResult).contains(12.30F, 15.46F, 16.99F, 45.00F);
    }

    @Test
    void findAllProductsByMedicineTypeAndManufacturer() {
        var filter = ProductFilter.builder()
                .medicineTypes(List.of(MedicineType.CAPSULES))
                .manufacturer("Bayer")
                .build();

        var result = productRepository.findByFilter(filter);

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getMedicineType)
                .toList();

        assertThat(actualResult).contains(MedicineType.CAPSULES);
    }


}
