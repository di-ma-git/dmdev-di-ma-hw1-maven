package com.dima.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class ManufacturerReadDto {

    Integer id;
    String name;
    String country;
    String description;
}
