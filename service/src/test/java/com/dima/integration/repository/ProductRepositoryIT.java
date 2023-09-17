package com.dima.integration.repository;

import com.dima.dto.filters.ProductFilter;
import com.dima.entity.Product;
import com.dima.enums.MedicineType;
import com.dima.repository.ProductRepository;
import com.dima.specification.ProductSpecification;
import com.dima.util.TestBase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ProductRepositoryIT extends TestBase {

    private final ProductRepository productRepository;

    @Test
    void sortProductsByPriceWithPagination() {
        var sortBy = Sort.sort(Product.class);
        var sort = sortBy.by(Product::getPrice);

        var pageable = PageRequest.of(1, 2, sort);

        var result = productRepository.findAllBy(pageable);

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getPrice)
                .toList();

        assertThat(actualResult).containsExactly(16.99F, 45.00F);

    }

    @Test
    void findAllProductsByProductCategoryWithSortAndPagination() {
        var sortBy = Sort.sort(Product.class);
        var sort = sortBy.by(Product::getName);

        var pageable = PageRequest.of(0, 2, sort);

        var result = productRepository.findAllByProductCategoryName("Painkillers", pageable);

        assertThat(result).hasSize(2);

    }

    @Test
    void sortProductsByPriceWithCountPagination() {
        var sortBy = Sort.sort(Product.class);
        var sort = sortBy.by(Product::getPrice);

        var pageable = PageRequest.of(1, 2, sort);

        var result = productRepository.findAllBy(pageable);

        assertThat(result).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(6);
        assertThat(result.getTotalPages()).isEqualTo(3);

        var actualResult = result.stream()
                .map(Product::getPrice)
                .toList();

        assertThat(actualResult).containsExactly(16.99F, 45.00F);

    }

    @Test
    void findAllProductsByName() {
        var filter = ProductFilter.builder()
                .name("Aspirin")
                .build();

        var result = productRepository.findAllByFilter(filter);

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getName)
                .findFirst();

        assertThat(actualResult.get()).contains("Aspirin");
    }

    @Test
    void findAllProductsByNameFragment() {
        var filter = ProductFilter.builder()
                .name("pirin")
                .build();

        var result = productRepository.findAllByFilter(filter);

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getName)
                .findFirst();

        assertThat(actualResult.get()).contains("Aspirin");
    }

    @Test
    void findAllProductsByNameFragmentPageable() {
        var sortBy = Sort.sort(Product.class);
        var sort = sortBy.by(Product::getPrice);

        var pageable = PageRequest.of(0, 2, sort);

        var filter = ProductFilter.builder()
                .name("pirin")
                .build();

        var specification = ProductSpecification.withFilter(filter);

        var result = productRepository.findAll(specification, pageable);

        assertThat(result).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getTotalPages()).isEqualTo(1);

        var actualResult = result.stream()
                .map(Product::getName)
                .findFirst();

        assertThat(actualResult.get()).contains("Aspirin");
    }

    @Test
    void findAllProductsByActiveSubstancePageable() {
        var sortBy = Sort.sort(Product.class);
        var sort = sortBy.by(Product::getName);

        var pageable = PageRequest.of(0, 10, sort);

        var filter = ProductFilter.builder()
                .activeSubstance("acid")
                .build();

        var specification = ProductSpecification.withFilter(filter);

        var result = productRepository.findAll(specification, pageable);

        assertThat(result).hasSize(6);
        assertThat(result.getTotalElements()).isEqualTo(6);
        assertThat(result.getTotalPages()).isEqualTo(1);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Aspirin", "Vitamin complex", "Vitamin C");
    }

    @Test
    void findAllProductsByManufacturerName() {
        var filter = ProductFilter.builder()
                .manufacturer("Pharmacom")
                .build();

        var result = productRepository.findAllByFilter(filter);

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Testosterone", "Boldenone");
    }

    @Test
    void findAllProductsByCategoryName() {
        var filter = ProductFilter.builder()
                .productCategory("Painkillers")
                .build();

        var result = productRepository.findAllByFilter(filter);

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Aspirin");
    }

    @Test
    void findAllProductsBySeveralMedicineType() {
        var filter = ProductFilter.builder()
                .medicineTypes(List.of(MedicineType.CAPSULES, MedicineType.INJECTIONS))
                .build();

        var result = productRepository.findAllByFilter(filter);

        assertThat(result).hasSize(6);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Aspirin");
    }

    @Test
    void findAllProductsByActiveSubstance() {
        var filter = ProductFilter.builder()
                .activeSubstance("Ascorbic acid")
                .build();

        var result = productRepository.findAllByFilter(filter);

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

        var result = productRepository.findAllByFilter(filter);

        assertThat(result).hasSize(3);

        var actualResult = result.stream()
                .map(Product::getPrice)
                .toList();

        assertThat(actualResult).contains(45.00F, 125.00F, 56.20F);
    }

    @Test
    void findAllProductsByFilterWithOnlyMaxPrice() {

        var filter = ProductFilter.builder()
                .priceMin(0F)
                .priceMax(50F)
                .build();

        var result = productRepository.findAllByFilter(filter);

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

        var result = productRepository.findAllByFilter(filter);

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getMedicineType)
                .toList();

        assertThat(actualResult).contains(MedicineType.CAPSULES);
    }
}
