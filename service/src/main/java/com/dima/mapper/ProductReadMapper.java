package com.dima.mapper;

import com.dima.dto.ProductReadDto;
import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductReadMapper implements Mapper<Product, ProductReadDto> {

    private final ManufacturerReadMapper manufacturerReadMapper;
    private final ProductCategoryReadMapper productCategoryReadMapper;
    private final ActiveSubstanceReadMapper activeSubstanceReadMapper;

    @Override
    public ProductReadDto map(Product object) {
        return ProductReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .description(object.getDescription())
                .price(object.getPrice())
                .quantityPerPackaging(object.getQuantityPerPackaging())
                .quantityPerDose(object.getQuantityPerDose())
                .medicineType(object.getMedicineType())
                .manufacturer(manufacturerReadMapper.map(object.getManufacturer()))
                .productCategory(productCategoryReadMapper.map(object.getProductCategory()))
                .activeSubstances(object.getProductActiveSubstances().stream()
                        .map(ProductActiveSubstance::getActiveSubstance)
                        .map(activeSubstanceReadMapper::map)
                        .toList()
                )
                .build();
    }
}
