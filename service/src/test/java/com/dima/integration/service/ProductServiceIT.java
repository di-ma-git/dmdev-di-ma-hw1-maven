package com.dima.integration.service;

import com.dima.dto.ActiveSubstanceReadDto;
import com.dima.dto.ProductCreateDto;
import com.dima.enums.MedicineType;
import com.dima.repository.ActiveSubstanceRepository;
import com.dima.repository.ManufacturerRepository;
import com.dima.repository.ProductCategoryRepository;
import com.dima.repository.ProductRepository;
import com.dima.service.ProductService;
import com.dima.testdata.TestSimpleData;
import com.dima.util.TestBase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ProductServiceIT extends TestBase {

    private final ProductService productService;

    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ActiveSubstanceRepository activeSubstanceRepository;

    @Test
    void findAll() {
        var result = productRepository.findAll();

        assertThat(result).hasSize(6);
    }

    @Test
    void findById() {
        var manufacturer = manufacturerRepository.save(TestSimpleData.getSimpleTestManufacturer());
        var productCategory = productCategoryRepository.save(TestSimpleData.getSimpleTestProductCategory());
        var product = TestSimpleData.getSimpleTestProduct();
        product.setManufacturer(manufacturer);
        product.setProductCategory(productCategory);
        productRepository.save(product);

        var result = productService.findById(product.getId());

        assertThat(result).isPresent();
        result.ifPresent(p -> {
            assertThat(p.getName()).isEqualTo(product.getName());
            assertThat(p.getPrice()).isEqualTo(product.getPrice());
            assertThat(p.getQuantityPerPackaging()).isEqualTo(product.getQuantityPerPackaging());
            assertThat(p.getQuantityPerDose()).isEqualTo(product.getQuantityPerDose());
            assertThat(p.getDescription()).isEqualTo(product.getDescription());
        });
    }

    @Test
    void create() {
        var manufacturer = manufacturerRepository.save(TestSimpleData.getSimpleTestManufacturer());
        var productCategory = productCategoryRepository.save(TestSimpleData.getSimpleTestProductCategory());
        var activeSubstance1 = activeSubstanceRepository.save(TestSimpleData.getSimpleTestActiveSubstance("AS1"));
        var activeSubstance2 = activeSubstanceRepository.save(TestSimpleData.getSimpleTestActiveSubstance("AS2"));

        ProductCreateDto productDto = ProductCreateDto.builder()
                .name("test")
                .price(12.50F)
                .description("Some description")
                .manufacturerId(manufacturer.getId())
                .productCategoryId(productCategory.getId())
                .medicineType(MedicineType.CAPSULES)
                .activeSubstancesIds(List.of(activeSubstance1.getId(), activeSubstance2.getId()))
                .build();

        var actualResult = productService.create(productDto);

        assertThat(actualResult.getName()).isEqualTo("test");
        assertThat(actualResult.getActiveSubstances()).hasSize(2);

    }

    @Test
    void update() {

        var product = productRepository.save(TestSimpleData.getSimpleTestProduct());
        var manufacturer = manufacturerRepository.save(TestSimpleData.getSimpleTestManufacturer());
        var productCategory = productCategoryRepository.save(TestSimpleData.getSimpleTestProductCategory());
        var activeSubstance1 = activeSubstanceRepository.save(TestSimpleData.getSimpleTestActiveSubstance("AS1"));
        var activeSubstance2 = activeSubstanceRepository.save(TestSimpleData.getSimpleTestActiveSubstance("AS2"));

        ProductCreateDto productDto = ProductCreateDto.builder()
                .name("test")
                .price(12.50F)
                .description("Some description")
                .manufacturerId(manufacturer.getId())
                .productCategoryId(productCategory.getId())
                .medicineType(MedicineType.CAPSULES)
                .activeSubstancesIds(List.of(activeSubstance1.getId(), activeSubstance2.getId()))
                .build();

        var actualResult = productService.update(product.getId(), productDto);

        assertThat(actualResult).isPresent();
        actualResult.ifPresent(p -> {
            assertThat(p.getName()).isEqualTo(productDto.getName());
            assertThat(p.getPrice()).isEqualTo(productDto.getPrice());
            assertThat(p.getDescription()).isEqualTo(productDto.getDescription());
            assertThat(p.getMedicineType()).isEqualTo(productDto.getMedicineType());
            assertThat(p.getManufacturer().getId()).isEqualTo(productDto.getManufacturerId());
            assertThat(p.getProductCategory().getId()).isEqualTo(productDto.getProductCategoryId());
            assertThat(p.getActiveSubstances().stream()
                    .map(ActiveSubstanceReadDto::getId)
                    .toList())
                    .contains(activeSubstance1.getId(), activeSubstance2.getId());
        });


    }

    @Test
    void delete() {
        var product = productRepository.save(TestSimpleData.getSimpleTestProduct());

        assertThat(productService.delete(product.getId())).isTrue();
        assertThat(productService.delete(-1L)).isFalse();
    }

}
