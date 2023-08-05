package com.dima.repository;

import com.dima.dao.CriteriaPredicate;
import com.dima.dto.ActiveSubstanceFilter;
import com.dima.entity.ActiveSubstance;
import com.dima.entity.ActiveSubstance_;
import com.dima.entity.Manufacturer_;
import com.dima.entity.ProductActiveSubstance_;
import com.dima.entity.Product_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveSubstanceRepository extends RepositoryBase<Integer, ActiveSubstance> {

    public ActiveSubstanceRepository(EntityManager entityManager) {
        super(ActiveSubstance.class, entityManager);
    }

    public List<ActiveSubstance> findByFilter(ActiveSubstanceFilter filter) {

        var cb = getEntityManager().getCriteriaBuilder();
        var criteria = cb.createQuery(ActiveSubstance.class);
        var activeSubstance = criteria.from(ActiveSubstance.class);
        var productActiveSubstance = activeSubstance.join(ActiveSubstance_.productActiveSubstances);
        var product = productActiveSubstance.join(ProductActiveSubstance_.product);
        var manufacturer = product.join(Product_.manufacturer);

        var predicateList = CriteriaPredicate.builder()
                .add(filter.getProductName(), value -> cb.equal(product.get(Product_.name), value))
                .add(filter.getProductManufacturerName(), value -> cb.equal(manufacturer.get(Manufacturer_.name), value))
                .build();
        var predicates = cb.and(predicateList.toArray(Predicate[]::new));

        criteria.select(activeSubstance).where(predicates);

        return getEntityManager().createQuery(criteria).getResultList();
    }
}
