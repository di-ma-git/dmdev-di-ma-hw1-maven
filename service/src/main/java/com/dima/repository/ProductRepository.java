package com.dima.repository;

import com.dima.dao.CriteriaPredicate;
import com.dima.dto.ProductFilter;
import com.dima.entity.ActiveSubstance_;
import com.dima.entity.Manufacturer_;
import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance_;
import com.dima.entity.ProductCategory_;
import com.dima.entity.Product_;
import com.dima.enums.MedicineType;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository extends RepositoryBase<Long, Product> {

    private ProductFilter filter;

    public ProductRepository(EntityManager entityManager) {
        super(Product.class, entityManager);
    }


    public List<Product> findAllProductsByName(String name) {

        filter = ProductFilter.builder()
                .name(name)
                .build();

        var cb = getEntityManager().getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(product.get(Product_.name), filter.getName()));

        criteria.select(product).where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(criteria).getResultList();
    }

    public List<Product> findByManufacturer(String name) {
        filter = ProductFilter.builder()
                .manufacturer(name)
                .build();

        var cb = getEntityManager().getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);
        var manufacturer = product.join(Product_.manufacturer);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(manufacturer.get(Manufacturer_.name), filter.getManufacturer()));

        criteria.select(product).where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(criteria).getResultList();
    }


    public List<Product> findByCategory(String name) {
        filter = ProductFilter.builder()
                .productCategory(name)
                .build();

        var cb = getEntityManager().getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);
        var productCategory = product.join(Product_.productCategory);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(productCategory.get(ProductCategory_.name), filter.getProductCategory()));

        criteria.select(product).where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(criteria).getResultList();
    }

    public List<Product> findByMedicineType(MedicineType... medicineTypes) {
        var filter = ProductFilter.builder()
                .medicineTypes(List.of(medicineTypes))
                .build();

        var cb = getEntityManager().getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        if (!filter.getMedicineTypes().isEmpty()) {
            predicates.add(product.get(Product_.medicineType).in(filter.getMedicineTypes()));
        }

        criteria.select(product).where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(criteria).getResultList();
    }

    public List<Product> findByActiveSubstance(String name) {
        filter = ProductFilter.builder()
                .activeSubstance(name)
                .build();

        var cb = getEntityManager().getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);
        var productActiveSubstance = product.join(Product_.productActiveSubstances);
        var activeSubstance = productActiveSubstance.join(ProductActiveSubstance_.activeSubstance);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getActiveSubstance() != null) {
            predicates.add(cb.equal(activeSubstance.get(ActiveSubstance_.name), filter.getActiveSubstance()));
        }

        criteria.select(product).where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(criteria).getResultList();
    }

    public List<Product> findByFilter(ProductFilter filter) {
        var cb = getEntityManager().getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);
        var productActiveSubstance = product.join(Product_.productActiveSubstances);
        var activeSubstance = productActiveSubstance.join(ProductActiveSubstance_.activeSubstance);
        var manufacturer = product.join(Product_.manufacturer);
        var productCategory = product.join(Product_.productCategory);

//        List<Predicate> predicates = new ArrayList<>();
//
//        if (filter.getActiveSubstance() != null) {
//            predicates.add(cb.equal(activeSubstance.get(ActiveSubstance_.name), filter.getActiveSubstance()));
//        }
//        if (filter.getManufacturer() != null) {
//            predicates.add(cb.equal(manufacturer.get(Manufacturer_.name), filter.getManufacturer()));
//        }
//        if (filter.getProductCategory() != null) {
//            predicates.add(cb.equal(productCategory.get(ProductCategory_.name), filter.getProductCategory()));
//        }
//
//        if (filter.getPriceMin() != null && filter.getPriceMax() != null) {
//            predicates.add(cb.between(product.get(Product_.price), filter.getPriceMin(), filter.getPriceMax()));
//        } else if (filter.getPriceMin() != null) {
//            predicates.add(cb.ge(product.get(Product_.price), filter.getPriceMin()));
//        } else if (filter.getPriceMax() != null) {
//            predicates.add(cb.le(product.get(Product_.price), filter.getPriceMax()));
//        }
//
//        if (!filter.getMedicineTypes().isEmpty()) {
//            predicates.add(product.get(Product_.medicineType).in(filter.getMedicineTypes()));
//        }


        var predicates = CriteriaPredicate.builder(cb)
                .add(filter.getName(), value -> cb.equal(product.get(Product_.name), value))
                .add(filter.getQuantityPerPackaging(), value -> cb.equal(product.get(Product_.quantityPerPackaging), value))
                .add(filter.getMedicineTypes(), value -> product.get(Product_.medicineType).in(value))
                .add(filter.getManufacturer(), value -> cb.equal(manufacturer.get(Manufacturer_.name), value))
                .add(filter.getActiveSubstance(), value -> cb.equal(activeSubstance.get(ActiveSubstance_.name), value))
                .add(filter.getProductCategory(), value -> cb.equal(productCategory.get(ProductCategory_.name), value))
                .addBetween(product.get(Product_.price), filter.getPriceMin(), filter.getPriceMax())
                .buildAnd();

        criteria.select(product).where(predicates).distinct(true);
        return entityManager.createQuery(criteria).getResultList();
    }
}
