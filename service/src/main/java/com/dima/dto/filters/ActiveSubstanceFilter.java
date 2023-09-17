package com.dima.dto.filters;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ActiveSubstanceFilter {

    String productName;
    String productManufacturerName;
}
