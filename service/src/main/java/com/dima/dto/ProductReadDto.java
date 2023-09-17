package com.dima.dto;

import com.dima.entity.ProductCategory;
import com.dima.enums.MedicineType;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class ProductReadDto {

    Long id;
    String name;
    Integer quantityPerPackaging;
    Double quantityPerDose;
    Float price;
    MedicineType medicineType;
    ManufacturerReadDto manufacturer;
    String description;
    ProductCategoryReadDto productCategory;
    List<ActiveSubstanceReadDto> activeSubstances;


    //    String image;
}
