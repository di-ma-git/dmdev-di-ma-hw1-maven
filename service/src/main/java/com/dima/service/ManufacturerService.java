package com.dima.service;

import com.dima.dto.ManufacturerReadDto;
import com.dima.mapper.ManufacturerReadMapper;
import com.dima.repository.ManufacturerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerReadMapper manufacturerReadMapper;

    public List<ManufacturerReadDto> findAll() {
        return manufacturerRepository.findAll().stream()
                .map(manufacturerReadMapper::map)
                .toList();
    }

}
