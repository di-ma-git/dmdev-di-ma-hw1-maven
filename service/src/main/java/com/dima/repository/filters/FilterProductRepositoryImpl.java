package com.dima.repository.filters;

import com.dima.dao.CriteriaPredicate;
import com.dima.dto.filters.ProductFilter;
import com.dima.entity.ActiveSubstance_;
import com.dima.entity.Manufacturer_;
import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance_;
import com.dima.entity.ProductCategory_;
import com.dima.entity.Product_;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FilterProductRepositoryImpl implements FilterProductRepository{

    private final EntityManager entityManager;

    @Override
    public List<Product> findAllByFilter(ProductFilter filter) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);
        var productActiveSubstance = product.join(Product_.productActiveSubstances);
        var activeSubstance = productActiveSubstance.join(ProductActiveSubstance_.activeSubstance);
        var manufacturer = product.join(Product_.manufacturer);
        var productCategory = product.join(Product_.productCategory);

        var predicatesList = CriteriaPredicate.builder()
                .add(filter.getName(), value -> cb.like(product.get(Product_.name), "%" + value + "%"))
                .add(filter.getQuantityPerPackaging(), value -> cb.equal(product.get(Product_.quantityPerPackaging), value))
                .add(filter.getMedicineTypes(), value -> product.get(Product_.medicineType).in(value))
                .add(filter.getManufacturer(), value -> cb.equal(manufacturer.get(Manufacturer_.name), value))
                .add(filter.getActiveSubstance(), value -> cb.equal(activeSubstance.get(ActiveSubstance_.name), value))
                .add(filter.getProductCategory(), value -> cb.equal(productCategory.get(ProductCategory_.name), value))
                .add(filter.getPriceMin(), filter.getPriceMax(), (p1, p2) -> cb.between(product.get(Product_.price), p1, p2))
                .build();

        var predicates = cb.and(predicatesList.toArray(Predicate[]::new));

        criteria.select(product).where(predicates).distinct(true);
        return entityManager.createQuery(criteria).getResultList();
    }
}
