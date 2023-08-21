package com.dima.repository.filters;

import com.dima.dao.CriteriaPredicate;
import com.dima.dto.filters.ManufacturerFilter;
import com.dima.entity.Manufacturer;
import com.dima.entity.Manufacturer_;
import com.dima.entity.Product;
import com.dima.entity.Product_;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FilterManufacturerRepositoryImpl implements FilterManufacturerRepository {

    private final EntityManager entityManager;

    @Override
    public List<Manufacturer> findAllByFilter(ManufacturerFilter filter) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Manufacturer.class);
        var manufacturer = criteria.from(Manufacturer.class);
        var products = manufacturer.join(Manufacturer_.products);

        var predicatesList = CriteriaPredicate.builder()
                .add(filter.getName(), value -> cb.like(manufacturer.get(Manufacturer_.name), "%" + value + "%"))
                .add(filter.getCountry(), value -> cb.like(manufacturer.get(Manufacturer_.country), "%" + value + "%"))
                .add(filter.getProductName(), value -> cb.like(products.get(Product_.name), "%" + value + "%"))
                .build();

        var predicates = cb.and(predicatesList.toArray(Predicate[]::new));

        criteria.select(manufacturer).where(predicates).distinct(true);
        return entityManager.createQuery(criteria).getResultList();
    }
}
