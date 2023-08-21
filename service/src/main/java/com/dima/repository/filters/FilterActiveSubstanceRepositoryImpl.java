package com.dima.repository.filters;

import com.dima.dao.CriteriaPredicate;
import com.dima.dto.filters.ActiveSubstanceFilter;
import com.dima.entity.ActiveSubstance;
import com.dima.entity.ActiveSubstance_;
import com.dima.entity.Manufacturer_;
import com.dima.entity.ProductActiveSubstance_;
import com.dima.entity.Product_;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FilterActiveSubstanceRepositoryImpl implements FilterActiveSubstanceRepository {

    private final EntityManager entityManager;

    @Override
    public List<ActiveSubstance> findAllByFilter(ActiveSubstanceFilter filter) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(ActiveSubstance.class);
        var activeSubstance = criteria.from(ActiveSubstance.class);
        var productActiveSubstance = activeSubstance.join(ActiveSubstance_.productActiveSubstances);
        var product = productActiveSubstance.join(ProductActiveSubstance_.product);
        var manufacturer = product.join(Product_.manufacturer);

        var predicateList = CriteriaPredicate.builder()
                .add(filter.getProductName(), value -> cb.like(product.get(Product_.name), "%" + value + "%"))
                .add(filter.getProductManufacturerName(), value -> cb.like(manufacturer.get(Manufacturer_.name), "%" + value + "%"))
                .build();
        var predicates = cb.and(predicateList.toArray(Predicate[]::new));

        criteria.select(activeSubstance).where(predicates);

        return entityManager.createQuery(criteria).getResultList();
    }
}
