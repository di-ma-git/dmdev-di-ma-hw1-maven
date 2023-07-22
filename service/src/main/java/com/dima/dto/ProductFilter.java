package com.dima.dto;

import com.dima.enums.MedicineType;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class ProductFilter {

    String name;
    Long quantityPerPackaging;

    @Builder.Default
    List<MedicineType> medicineTypes = new ArrayList<>();
    String manufacturer;
    String productCategory;
    Float priceMin;
    Float priceMax;
    String activeSubstance;

    void addMedicineType(MedicineType type) {
        this.medicineTypes.add(type);
    }

}
