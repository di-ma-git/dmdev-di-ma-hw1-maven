package com.dima.integration.repository;

import com.dima.entity.Manufacturer;
import com.dima.repository.ManufacturerRepository;
import com.dima.testdata.TestSimpleData;
import com.dima.util.TestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ManufacturerRepositoryIT extends TestBase {

    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerRepositoryIT(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Test
    void saveManufacturerSuccessful() {
        var manufacturer = TestSimpleData.getSimpleTestManufacturer();

        manufacturerRepository.save(manufacturer);
        entityManager.flush();
        entityManager.clear();

        assertThat(manufacturer.getId()).isNotNull();
    }

    @Test
    void findManufacturerSuccessful() {
        var manufacturer = TestSimpleData.getSimpleTestManufacturer();
        var product = TestSimpleData.getSimpleTestProduct();
        manufacturer.addProduct(product);

        manufacturerRepository.save(manufacturer);
        entityManager.flush();
        entityManager.clear();

        var actualResult = manufacturerRepository.findById(manufacturer.getId()).get();

        assertThat(actualResult.getId()).isEqualTo(manufacturer.getId());
        assertThat(actualResult).isEqualTo(manufacturer);
        assertThat(actualResult.getProducts()).hasSize(1);
    }

    @Test
    void findManufacturerSuccessfulWithAllProducts() {
        var manufacturer = TestSimpleData.getSimpleTestManufacturer();
        var product1 = TestSimpleData.getSimpleTestProduct();
        var product2 = TestSimpleData.getSimpleTestProduct();
        manufacturer.addProduct(product1);
        manufacturer.addProduct(product2);

        manufacturerRepository.save(manufacturer);
        entityManager.flush();
        entityManager.clear();

        var actualResult = manufacturerRepository.findById(manufacturer.getId()).get();

        assertThat(actualResult.getId()).isEqualTo(manufacturer.getId());
        assertThat(actualResult).isEqualTo(manufacturer);
        assertThat(actualResult.getProducts()).hasSize(2);
    }

    @Test
    void updateManufacturerSuccessful() {
        var manufacturer = TestSimpleData.getSimpleTestManufacturer();
        var product = TestSimpleData.getSimpleTestProduct();
        manufacturer.addProduct(product);

        manufacturerRepository.save(manufacturer);
        entityManager.flush();
        entityManager.clear();
        manufacturer.setDescription("Another description");
        manufacturerRepository.update(manufacturer);
        entityManager.flush();
        entityManager.clear();

        var actualResult = manufacturerRepository.findById(manufacturer.getId()).get();

        assertThat(actualResult.getDescription()).isEqualTo(manufacturer.getDescription());
    }

    @Test
    void deleteManufacturerSuccessful() {
        var manufacturer = TestSimpleData.getSimpleTestManufacturer();
        var product = TestSimpleData.getSimpleTestProduct();
        manufacturer.addProduct(product);

        manufacturerRepository.save(manufacturer);
        entityManager.flush();
        entityManager.clear();
        manufacturerRepository.delete(manufacturer);

        var actualResult = manufacturerRepository.findById(manufacturer.getId());

        assertThat(actualResult).isEmpty();
    }

    @Test
    void findAllManufacturers() {

        var actualResult = manufacturerRepository.findAll();

        assertThat(actualResult).hasSize(4);
    }

    @Test
    void findManufacturerByCountry() {
        var result = manufacturerRepository.findByCountry("USA");

        assertThat(result).hasSize(2);

        var actualResult = result.stream().map(Manufacturer::getName).toList();

        assertThat(actualResult).containsExactlyInAnyOrder("Pfizer", "Pharmacom");
    }

    @Test
    void findManufacturerByName() {
        var actualResult = manufacturerRepository.findByName("Pfizer");

        assertThat(actualResult.getName()).isEqualTo("Pfizer");
    }

    @Test
    void findManufacturerByProduct() {
        var result = manufacturerRepository.findByProductName("Aspirin");

        var actualResult = result.stream().map(Manufacturer::getName).toList();

        assertThat(actualResult).containsExactlyInAnyOrder("Pfizer", "Bayer");
    }
}