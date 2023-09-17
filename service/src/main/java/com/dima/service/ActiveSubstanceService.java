package com.dima.service;

import com.dima.dto.ActiveSubstanceReadDto;
import com.dima.mapper.ActiveSubstanceReadMapper;
import com.dima.repository.ActiveSubstanceRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActiveSubstanceService {

    private final ActiveSubstanceRepository activeSubstanceRepository;
    private final ActiveSubstanceReadMapper activeSubstanceReadMapper;

    public List<ActiveSubstanceReadDto> findAll() {
        return activeSubstanceRepository.findAll().stream()
                .map(activeSubstanceReadMapper::map)
                .toList();
    }
}
