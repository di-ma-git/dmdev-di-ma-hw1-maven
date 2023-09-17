package com.dima.service;

import com.dima.dto.ProductCreateDto;
import com.dima.dto.ProductReadDto;
import com.dima.mapper.ProductCreateMapper;
import com.dima.mapper.ProductReadMapper;
import com.dima.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductReadMapper productReadMapper;
    private final ProductCreateMapper productCreateMapper;
    private final ImageService imageService;

    public List<ProductReadDto> findAll() {
        return productRepository.findAll().stream()
                .map(productReadMapper::map)
                .toList();
    }

    public Optional<ProductReadDto> findById(Long id) {
        return productRepository.findById(id)
                .map(productReadMapper::map);
    }

    @Transactional
    public ProductReadDto create(ProductCreateDto productDto) {
        return Optional.of(productDto)
                .map(productCreateMapper::map)
                .map(productRepository::save)
                .map(productReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ProductReadDto> update(Long id, ProductCreateDto productDto) {
        return productRepository.findById(id)
                .map(entity -> productCreateMapper.map(productDto, entity))
                .map(productRepository::saveAndFlush)
                .map(productReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return productRepository.findById(id)
                .map(entity -> {
                    productRepository.delete(entity);
                    productRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
