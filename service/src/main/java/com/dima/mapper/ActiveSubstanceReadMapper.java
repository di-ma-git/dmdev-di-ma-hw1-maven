package com.dima.mapper;

import com.dima.dto.ActiveSubstanceReadDto;
import com.dima.entity.ActiveSubstance;
import org.springframework.stereotype.Component;

@Component
public class ActiveSubstanceReadMapper implements Mapper<ActiveSubstance, ActiveSubstanceReadDto> {

    @Override
    public ActiveSubstanceReadDto map(ActiveSubstance object) {
        return ActiveSubstanceReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .description(object.getDescription())
                .build();
    }
}
