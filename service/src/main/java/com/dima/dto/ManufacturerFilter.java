package com.dima.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ManufacturerFilter {

    String name;
    String country;
    String productName;
}
