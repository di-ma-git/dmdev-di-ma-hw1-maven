package com.dima.dto;

import com.dima.entity.Manufacturer;
import com.dima.entity.ProductActiveSubstance;
import com.dima.entity.ProductCategory;
import com.dima.enums.MedicineType;
import com.dima.repository.ActiveSubstanceRepository;
import com.dima.repository.ManufacturerRepository;
import com.dima.repository.ProductCategoryRepository;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

@Value
@Builder
@FieldNameConstants
@AllArgsConstructor
public class ProductCreateDto {

    @NotEmpty
    String name;
    Integer quantityPerPackaging;
    Double quantityPerDose;
    String description;
    MedicineType medicineType;
    Float price;
    Integer productCategoryId;
    @NotEmpty
    Integer manufacturerId;
    List<Integer> activeSubstancesIds;
    MultipartFile image;

}
