package com.dima.repositoryIntegration;

import com.dima.entity.Manufacturer;
import com.dima.repository.ManufacturerRepository;
import com.dima.testData.TestSimpleData;
import com.dima.util.TestBaseEntityManager;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ManufacturerRepositoryIT extends TestBaseEntityManager {

    private final ManufacturerRepository manufacturerRepository = new ManufacturerRepository(session);

    @Test
    void saveManufacturerSuccessful() {
        var manufacturer = TestSimpleData.getSimpleTestManufacturer();

        manufacturerRepository.save(manufacturer);
        session.flush();
        session.clear();

        assertThat(manufacturer.getId()).isNotNull();
    }

    @Test
    void findManufacturerSuccessful() {
        var manufacturer = TestSimpleData.getSimpleTestManufacturer();
        var product = TestSimpleData.getSimpleTestProduct();
        manufacturer.addProduct(product);

        manufacturerRepository.save(manufacturer);
        session.flush();
        session.clear();

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
        session.flush();
        session.clear();

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
        session.flush();
        session.clear();
        manufacturer.setDescription("Another description");
        manufacturerRepository.update(manufacturer);
        session.flush();
        session.clear();

        var actualResult = manufacturerRepository.findById(manufacturer.getId()).get();

        assertThat(actualResult.getDescription()).isEqualTo(manufacturer.getDescription());
    }

    @Test
    void deleteManufacturerSuccessful() {
        var manufacturer = TestSimpleData.getSimpleTestManufacturer();
        var product = TestSimpleData.getSimpleTestProduct();
        manufacturer.addProduct(product);

        manufacturerRepository.save(manufacturer);
        session.flush();
        session.clear();
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
