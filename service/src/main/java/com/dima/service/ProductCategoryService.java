package com.dima.service;

import com.dima.dto.ProductCategoryReadDto;
import com.dima.mapper.ProductCategoryReadMapper;
import com.dima.repository.ProductCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryReadMapper productCategoryReadMapper;

    public List<ProductCategoryReadDto> findAll() {
        return productCategoryRepository.findAll().stream()
                .map(productCategoryReadMapper::map)
                .toList();
    }
}
