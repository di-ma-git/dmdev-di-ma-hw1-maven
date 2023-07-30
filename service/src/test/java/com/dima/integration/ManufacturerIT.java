package com.dima.integration;

import com.dima.entity.Manufacturer;
import org.junit.jupiter.api.Test;
import com.dima.util.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class ManufacturerIT extends TestBase {

    @Test
    void saveManufacturerSuccessful() {
        var manufacturer = testSimpleData.getSimpleTestManufacturer();
        var product = testSimpleData.getSimpleTestProduct();
        product.setManufacturer(manufacturer);

        session.beginTransaction();
        session.save(manufacturer);

        assertThat(manufacturer.getId()).isNotNull();
    }

    @Test
    void getManufacturerSuccessful() {
        var manufacturer = testSimpleData.getSimpleTestManufacturer();
        var product = testSimpleData.getSimpleTestProduct();
        product.setManufacturer(manufacturer);

        session.beginTransaction();
        session.save(manufacturer);
        session.save(product);
        session.flush();
        session.clear();

        var actualResult = session.get(Manufacturer.class, manufacturer.getId());

        assertThat(actualResult.getId()).isEqualTo(manufacturer.getId());
        assertThat(actualResult.getProducts()).hasSize(1);
    }
    @Test
    void updateManufacturerSuccessful() {
        var manufacturer = testSimpleData.getSimpleTestManufacturer();
        var product = testSimpleData.getSimpleTestProduct();
        product.setManufacturer(manufacturer);

        session.beginTransaction();
        session.save(manufacturer);
        session.save(product);
        session.flush();
        session.clear();
        manufacturer.setDescription("Another description");
        session.update(manufacturer);
        session.flush();
        session.clear();

        var actualResult = session.get(Manufacturer.class, manufacturer.getId());

        assertThat(actualResult.getDescription()).isEqualTo(manufacturer.getDescription());
    }
    @Test
    void deleteManufacturerSuccessful() {
        var manufacturer = testSimpleData.getSimpleTestManufacturer();
        var product = testSimpleData.getSimpleTestProduct();
        product.setManufacturer(manufacturer);

        session.beginTransaction();
        session.save(manufacturer);
        session.save(product);
        session.flush();
        session.clear();
        session.delete(manufacturer);

        var actualResult = session.get(Manufacturer.class, manufacturer.getId());

        assertThat(actualResult).isNull();
    }
}
