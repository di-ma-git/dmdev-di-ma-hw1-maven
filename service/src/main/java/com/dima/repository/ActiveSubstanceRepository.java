package com.dima.repository;

import com.dima.dto.ActiveSubstanceFilter;
import com.dima.entity.ActiveSubstance;
import com.dima.entity.ActiveSubstance_;
import com.dima.entity.Manufacturer;
import com.dima.entity.Manufacturer_;
import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance_;
import com.dima.entity.Product_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ActiveSubstanceRepository extends RepositoryBase<Integer, ActiveSubstance> {

    private ActiveSubstanceFilter filter;
    public ActiveSubstanceRepository(EntityManager entityManager) {
        super(ActiveSubstance.class, entityManager);
    }

    public List<ActiveSubstance> findByProduct(String productName, String productManufacturer) {

        filter = ActiveSubstanceFilter.builder()
                .productName(productName)
                .productManufacturer(productManufacturer)
                .build();

        var cb = getEntityManager().getCriteriaBuilder();
        var criteria = cb.createQuery(ActiveSubstance.class);
        var activeSubstance = criteria.from(ActiveSubstance.class);
        var productActiveSubstance = activeSubstance.join(ActiveSubstance_.productActiveSubstances);
        var product = productActiveSubstance.join(ProductActiveSubstance_.product);
        var manufacturer = product.join(Product_.manufacturer);


        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(product.get(Product_.name), filter.getProductName()));
        predicates.add(cb.equal(manufacturer.get(Manufacturer_.name), filter.getProductManufacturer()));

        criteria.select(activeSubstance).where(predicates.toArray(Predicate[]::new));

        return getEntityManager().createQuery(criteria).getResultList();
    }
}
