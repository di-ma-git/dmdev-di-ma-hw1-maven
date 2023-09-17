package com.dima.mapper;

import com.dima.dto.ProductCreateDto;
import com.dima.entity.Manufacturer;
import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance;
import com.dima.entity.ProductCategory;
import com.dima.repository.ActiveSubstanceRepository;
import com.dima.repository.ManufacturerRepository;
import com.dima.repository.ProductActiveSubstanceRepository;
import com.dima.repository.ProductCategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCreateMapper implements Mapper<ProductCreateDto, Product> {

    private final ManufacturerRepository manufacturerRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ActiveSubstanceRepository activeSubstanceRepository;
    private final ProductActiveSubstanceRepository productActiveSubstanceRepository;

    @Override
    public Product map(ProductCreateDto fromObject, Product toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Product map(ProductCreateDto object) {
        Product product = new Product();
        copy(object, product);
        return product;
    }

    public void copy(ProductCreateDto object, Product product) {
        product.setName(object.getName());
        product.setPrice(object.getPrice());
        product.setQuantityPerPackaging(object.getQuantityPerPackaging());
        product.setQuantityPerDose(object.getQuantityPerDose());
        product.setMedicineType(object.getMedicineType());
        product.setDescription(object.getDescription());
        product.setManufacturer(getManufacturer(object.getManufacturerId()));
        product.setProductCategory(getProductCategory(object.getProductCategoryId()));
        product.getProductActiveSubstances().clear();
        product.getProductActiveSubstances().addAll(getProductActiveSubstances(object.getActiveSubstancesIds(), product));

    }

    private List<ProductActiveSubstance> getProductActiveSubstances(List<Integer> activeSubstancesIds, Product product) {
//        productActiveSubstanceRepository.deleteAllByProductId(product.getId());
        if (activeSubstancesIds == null) {
            return new ArrayList<>();
        }

        return activeSubstancesIds.stream()
                .map(id -> {
                    return productActiveSubstanceRepository.save(ProductActiveSubstance.builder()
                            .product(product)
                            .activeSubstance(activeSubstanceRepository.findById(id).get())
                            .build());
                })
                .toList();
    }

    private Manufacturer getManufacturer(Integer manufacturerId) {
        return Optional.ofNullable(manufacturerId)
                .flatMap(manufacturerRepository::findById)
                .orElse(null);
    }

    private ProductCategory getProductCategory(Integer productCategoryId) {
        return Optional.ofNullable(productCategoryId)
                .flatMap(productCategoryRepository::findById)
                .orElse(null);
    }
}
