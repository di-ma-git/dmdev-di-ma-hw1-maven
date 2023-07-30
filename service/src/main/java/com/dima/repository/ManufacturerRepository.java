package com.dima.repository;

import com.dima.dto.ManufacturerFilter;
import com.dima.entity.Manufacturer;
import com.dima.entity.Manufacturer_;
import com.dima.entity.Product_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ManufacturerRepository extends RepositoryBase<Integer, Manufacturer> {

    private ManufacturerFilter filter;

    public ManufacturerRepository(EntityManager entityManager) {
        super(Manufacturer.class, entityManager);
    }

    public List<Manufacturer> findByCountry(String country) {

        filter = ManufacturerFilter.builder()
                .country(country)
                .build();

        var cb = getEntityManager().getCriteriaBuilder();
        var criteria = cb.createQuery(Manufacturer.class);
        var manufacturer = criteria.from(Manufacturer.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(manufacturer.get(Manufacturer_.country), filter.getCountry()));

        criteria.select(manufacturer).where(predicates.toArray(Predicate[]::new));

        return getEntityManager().createQuery(criteria).getResultList();
    }

    public Manufacturer findByName(String name) {

        filter = ManufacturerFilter.builder()
                .name(name)
                .build();

        var cb = getEntityManager().getCriteriaBuilder();
        var criteria = cb.createQuery(Manufacturer.class);
        var manufacturer = criteria.from(Manufacturer.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(manufacturer.get(Manufacturer_.name), filter.getName()));

        criteria.select(manufacturer).where(predicates.toArray(Predicate[]::new));

        return getEntityManager().createQuery(criteria).getResultList().get(0);
    }

    public List<Manufacturer> findByProductName(String productName) {

        filter = ManufacturerFilter.builder()
                .productName(productName)
                .build();

        var cb = getEntityManager().getCriteriaBuilder();
        var criteria = cb.createQuery(Manufacturer.class);
        var manufacturer = criteria.from(Manufacturer.class);
        var productList = manufacturer.join(Manufacturer_.products);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(productList.get(Product_.name), filter.getProductName()));

        criteria.select(manufacturer).where(predicates.toArray(Predicate[]::new));

        return getEntityManager().createQuery(criteria).getResultList();
    }


}
