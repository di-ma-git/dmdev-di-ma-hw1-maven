package com.dima.criteriaIntegration;

import com.dima.dto.ProductFilter;
import com.dima.entity.ActiveSubstance;
import com.dima.entity.ActiveSubstance_;
import com.dima.entity.Manufacturer_;
import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance_;
import com.dima.entity.ProductCategory_;
import com.dima.entity.Product_;
import com.dima.enums.MedicineType;
import com.dima.util.TestBase;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductFilterIT extends TestBase {

    @Test
    void findAllProducts() {
        session.beginTransaction();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);
        criteria.select(product);
        var result = session.createQuery(criteria).stream().toList();

        assertThat(result).hasSize(6);

    }

    @Test
    void findAllProductsByName() {
        ProductFilter filter = ProductFilter.builder()
                .name("Aspirin")
                .build();

        session.beginTransaction();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class); // таблица

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(product.get(Product_.name), filter.getName()));

        criteria.select(product).where(predicates.toArray(Predicate[]::new));

        var result = session.createQuery(criteria).stream().toList();

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getName)
                .findFirst();

        assertThat(actualResult.get()).contains("Aspirin");
    }

    @Test
    void findAllProductsByManufacturer() {
        var filter = ProductFilter.builder()
                .manufacturer("Pharmacom")
                .build();

        session.beginTransaction();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);
        var manufacturer = product.join(Product_.manufacturer);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(manufacturer.get(Manufacturer_.name), filter.getManufacturer()));

        criteria.select(product).where(predicates.toArray(Predicate[]::new));

        var result = session.createQuery(criteria).list();

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Testosterone", "Boldenone");
    }

    @Test
    void findAllProductsByCategory() {
        var filter = ProductFilter.builder()
                .productCategory("Painkillers")
                .build();

        session.beginTransaction();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);
        var productCategory = product.join(Product_.productCategory);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(productCategory.get(ProductCategory_.name), filter.getProductCategory()));

        criteria.select(product).where(predicates.toArray(Predicate[]::new));

        var result = session.createQuery(criteria).list();

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Aspirin");
    }

    @Test
    void findAllProductsBySeveralMedicineType() {
        var filter = ProductFilter.builder()
                .medicineTypes(List.of(MedicineType.CAPSULES, MedicineType.INJECTIONS))
                .build();

        session.beginTransaction();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(product.get(Product_.medicineType).in(filter.getMedicineTypes()));

        criteria.select(product).where(predicates.toArray(Predicate[]::new));

        var result = session.createQuery(criteria).list();

        assertThat(result).hasSize(6);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Aspirin");
    }

    @Test
    void findAllProductsByMedicineTypeAndManufacturer() {
        var filter = ProductFilter.builder()
                .medicineTypes(List.of(MedicineType.CAPSULES))
                .manufacturer("Sinopharm")
                .build();

        session.beginTransaction();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);
        var manufacturer = product.join(Product_.manufacturer);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(product.get(Product_.medicineType).in(filter.getMedicineTypes()));
        predicates.add(cb.equal(manufacturer.get(Manufacturer_.name), filter.getManufacturer()));


        criteria.select(product).where(predicates.toArray(Predicate[]::new));

        var result = session.createQuery(criteria).list();

        assertThat(result).hasSize(1);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Vitamin complex");
    }

    @Test
    void findProductsByActiveSubstance() {
        var filter = ProductFilter.builder()
                .activeSubstance("Ascorbic acid")
                .build();

        session.beginTransaction();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);
        var productActiveSubstance = product.join(Product_.productActiveSubstances);
        var activeSubstance = productActiveSubstance.join(ProductActiveSubstance_.activeSubstance);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(activeSubstance.get(ActiveSubstance_.name), filter.getActiveSubstance()));

        criteria.select(product).where(predicates.toArray(Predicate[]::new));

        var result = session.createQuery(criteria).list();

        assertThat(result).hasSize(4);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Aspirin", "Vitamin complex", "Vitamin C");
    }

    @Test
    void findByActiveSubstanceByProductName() {
        var filter = ProductFilter.builder()
                .name("Aspirin")
                .build();

        session.beginTransaction();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(ActiveSubstance.class);
        var activeSubstance = criteria.from(ActiveSubstance.class);
        var productActiveSubstance = activeSubstance.join(ActiveSubstance_.productActiveSubstances);
        var product = productActiveSubstance.join(ProductActiveSubstance_.product);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(product.get(Product_.name), filter.getName()));

        criteria.select(activeSubstance).where(predicates.toArray(Predicate[]::new)).distinct(true);

        var result = session.createQuery(criteria).list();

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(ActiveSubstance::getName)
                .toList();

        assertThat(actualResult).contains("Acetylsalicylic acid", "Ascorbic acid");
    }

    @Test
    void findProductsWherePriceBetween() {
        var filter = ProductFilter.builder()
                .priceMin(30F)
                .priceMax(300F)
                .build();

        session.beginTransaction();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.between(product.get(Product_.price), filter.getPriceMin(), filter.getPriceMax()));

        criteria.select(product).where(predicates.toArray(Predicate[]::new)).orderBy(cb.asc(product.get(Product_.price)));

        var result = session.createQuery(criteria).list();

        assertThat(result).hasSize(3);

        var actualResult = result.stream()
                .map(Product::getPrice)
                .toList();

        assertThat(actualResult).contains(45.00F, 125.00F, 56.20F);
        assertThat(actualResult.get(2)).isEqualTo(125.00F);
    }
}
