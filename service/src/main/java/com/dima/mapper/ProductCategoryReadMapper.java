package com.dima.mapper;

import com.dima.dto.ProductCategoryReadDto;
import com.dima.entity.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryReadMapper implements Mapper<ProductCategory, ProductCategoryReadDto> {
    @Override
    public ProductCategoryReadDto map(ProductCategory object) {
        return ProductCategoryReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .description(object.getDescription())
                .build();
    }
}
