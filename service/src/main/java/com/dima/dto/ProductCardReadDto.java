package com.dima.dto;

import com.dima.enums.MedicineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class ProductCardReadDto {

    Long id;
    String name;
    String image;
    Float price;
    Integer quantityPerPackaging;
    Double quantityPerDose;
    MedicineType medicineType;
    ManufacturerReadDto manufacturer;

}
