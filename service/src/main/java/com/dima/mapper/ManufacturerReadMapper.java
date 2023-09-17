package com.dima.mapper;

import com.dima.dto.ManufacturerReadDto;
import com.dima.entity.Manufacturer;
import org.springframework.stereotype.Component;

@Component

public class ManufacturerReadMapper implements Mapper<Manufacturer, ManufacturerReadDto> {

    @Override
    public ManufacturerReadDto map(Manufacturer object) {
        return ManufacturerReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .country(object.getCountry())
                .description(object.getDescription())
                .build();
    }
}
