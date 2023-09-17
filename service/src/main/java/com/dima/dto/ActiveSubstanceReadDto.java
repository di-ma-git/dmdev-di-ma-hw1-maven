package com.dima.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class ActiveSubstanceReadDto {

    Integer id;
    String name;
    String description;
}
