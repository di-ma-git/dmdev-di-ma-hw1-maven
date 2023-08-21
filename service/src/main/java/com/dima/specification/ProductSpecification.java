package com.dima.specification;

import com.dima.dao.CriteriaPredicate;
import com.dima.dto.filters.ProductFilter;
import com.dima.entity.ActiveSubstance_;
import com.dima.entity.Manufacturer_;
import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance_;
import com.dima.entity.ProductCategory_;
import com.dima.entity.Product_;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> withFilter(ProductFilter filter) {
        return (product, query, cb) -> {
            var predicatesList = CriteriaPredicate.builder()
                    .add(filter.getName(), value -> cb.like(product.get(Product_.name), "%" + value + "%"))
                    .add(filter.getQuantityPerPackaging(), value -> cb.equal(product.get(Product_.quantityPerPackaging), value))
                    .add(filter.getMedicineTypes(), value -> product.get(Product_.medicineType).in(value))
                    .add(filter.getManufacturer(), value -> cb.equal(product.join(Product_.manufacturer).get(Manufacturer_.name), value))
                    .add(filter.getActiveSubstance(), value -> cb.like(product.join(Product_.productActiveSubstances).join(ProductActiveSubstance_.activeSubstance).get(ActiveSubstance_.name), "%" + value + "%"))
                    .add(filter.getProductCategory(), value -> cb.equal(product.join(Product_.productCategory).get(ProductCategory_.name), value))
                    .add(() -> filter.getPriceMin() != null && filter.getPriceMax() != null, () -> cb.between(product.get(Product_.price), filter.getPriceMin(), filter.getPriceMax()))
                    .build();

            return cb.and(predicatesList.toArray(Predicate[]::new));
        };
    }
}
