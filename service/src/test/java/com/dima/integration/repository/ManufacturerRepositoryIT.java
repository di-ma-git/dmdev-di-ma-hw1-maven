package com.dima.integration.repository;

import com.dima.dto.filters.ManufacturerFilter;
import com.dima.entity.Manufacturer;
import com.dima.entity.Product;
import com.dima.repository.ManufacturerRepository;
import com.dima.util.TestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ManufacturerRepositoryIT extends TestBase {

    private final ManufacturerRepository manufacturerRepository;

    @Test
    void findAllManufacturers() {

        var actualResult = manufacturerRepository.findAll();

        assertThat(actualResult).hasSize(4);
    }

    @Test
    void findManufacturerByCountry() {
        var result = manufacturerRepository.findAllByCountry("USA");

        assertThat(result).hasSize(2);

        var actualResult = result.stream().map(Manufacturer::getName).toList();

        assertThat(actualResult).containsExactlyInAnyOrder("Pfizer", "Pharmacom");
    }

    @Test
    void findManufacturerByName() {
        var actualResult = manufacturerRepository.findByName("Pfizer");

        assertThat(actualResult.get().getName()).isEqualTo("Pfizer");
    }

    @Test
    void findManufacturersByNameFragment() {
        var result = manufacturerRepository.findAllByNameFragment("er");

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Manufacturer::getName)
                .toList();

        assertThat(actualResult).contains("Pfizer", "Bayer");
    }

    @Test
    void findManufacturerByNameWithProducts() {
        var manufacturer = manufacturerRepository.findAllByNameWithProducts("Pharmacom");

        var products = manufacturer.get().getProducts();

        assertThat(products).hasSize(2);

        var actualResult = products.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Testosterone", "Boldenone");
    }

    @Test
    void findAllManufacturerByCountryFilter() {
        var filter = ManufacturerFilter.builder()
                .country("US")
                .build();

        var result = manufacturerRepository.findAllByFilter(filter);

        assertThat(result).hasSize(2);

    }

}
