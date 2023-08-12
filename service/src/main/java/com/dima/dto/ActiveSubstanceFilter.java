package com.dima.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ActiveSubstanceFilter {

    String productName;
    String productManufacturerName;
}
