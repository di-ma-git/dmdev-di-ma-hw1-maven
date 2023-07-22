package com.dima.querydslIntegration;

import com.dima.dao.QPredicate;
import com.dima.dto.ProductFilter;
import com.dima.entity.ActiveSubstance;
import com.dima.entity.Product;
import com.dima.enums.MedicineType;
import com.dima.util.TestBase;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.dima.entity.QActiveSubstance.activeSubstance;
import static com.dima.entity.QManufacturer.manufacturer;
import static com.dima.entity.QProduct.product;
import static com.dima.entity.QProductActiveSubstance.productActiveSubstance;
import static com.dima.entity.QProductCategory.productCategory;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductFilterIT extends TestBase {

    @Test
    void findAllProductsByName() {
        session.beginTransaction();
        ProductFilter filter = ProductFilter.builder()
                .name("Aspirin")
                .build();
        var predicate = QPredicate.builder()
                .add(filter.getName(), product.name::eq)
                .buildAnd();
        var result = new JPAQuery<Product>(session)
                .select(product)
                .from(product)
                .where(predicate)
                .fetch();

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getName)
                .findFirst();

        assertThat(actualResult.get()).contains("Aspirin");
    }

    @Test
    void findAllProductsByManufacturer() {
        session.beginTransaction();
        var filter = ProductFilter.builder()
                .manufacturer("Pharmacom")
                .build();

        var predicate = QPredicate.builder()
                .add(filter.getManufacturer(), manufacturer.name::eq)
                .buildAnd();

        var result = new JPAQuery<Product>(session)
                .select(product)
                .from(product)
                .join(product.manufacturer, manufacturer)
                .where(predicate)
                .fetch();

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Testosterone", "Boldenone");
    }

    @Test
    void findAllProductsByCategory() {
        session.beginTransaction();
        var filter = ProductFilter.builder()
                .productCategory("Painkillers")
                .build();

        var predicate = QPredicate.builder()
                .add(filter.getProductCategory(), productCategory.name::eq)
                .buildOr();

        var result = new JPAQuery<Product>(session)
                .select(product)
                .from(productCategory)
                .join(productCategory.products, product)
                .where(predicate)
                .fetch();

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Aspirin");
    }

    @Test
    void findAllProductsByMedicineTypeAnd() {
        session.beginTransaction();
        var filter = ProductFilter.builder()
                .medicineTypes(List.of(MedicineType.CAPSULES, MedicineType.INJECTIONS))
                .build();
        var predicate = QPredicate.builder()
                .add(filter.getMedicineTypes(), product.medicineType::in)
                .buildAnd();
        var result = new JPAQuery<Product>(session)
                .select(product)
                .from(product)
                .where(predicate)
                .fetch();

        assertThat(result).hasSize(6);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Aspirin");
    }

    @Test
    void findAllProductsBySeveralMedicineTypeOr() {
        session.beginTransaction();
        var filter = ProductFilter.builder()
                .medicineTypes(List.of(MedicineType.CAPSULES, MedicineType.TABLETS))
                .build();
        var predicate = QPredicate.builder()
                .add(filter.getMedicineTypes(), product.medicineType::in)
                .buildOr();
        var result = new JPAQuery<Product>(session)
                .select(product)
                .from(product)
                .where(predicate)
                .fetch();

        assertThat(result).hasSize(4);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Aspirin");
    }

    @Test
    void findAllProductsByMedicineTypeAndManufacturer() {
        session.beginTransaction();
        var filter = ProductFilter.builder()
                .medicineTypes(List.of(MedicineType.CAPSULES))
                .manufacturer("Sinopharm")
                .build();
        var predicate = QPredicate.builder()
                .add(filter.getMedicineTypes().get(0), product.medicineType::eq)
                .add(filter.getManufacturer(), manufacturer.name::eq)
                .buildAnd();
        var result = new JPAQuery<Product>(session)
                .select(product)
                .from(product)
                .where(predicate)
                .fetch();

        assertThat(result).hasSize(1);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Vitamin complex");
    }

    @Test
    void findProductsByActiveSubstance() {
        session.beginTransaction();
        var filter = ProductFilter.builder()
                .activeSubstance("Ascorbic acid")
                .build();
        var predicate = QPredicate.builder()
                .add(filter.getActiveSubstance(), activeSubstance.name::eq)
                .buildOr();
        var result = new JPAQuery<Product>(session)
                .select(product)
                .from(product)
                .join(product.productActiveSubstances, productActiveSubstance)
                .join(productActiveSubstance.activeSubstance, activeSubstance)
                .where(predicate)
                .fetch();

        assertThat(result).hasSize(4);

        var actualResult = result.stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Aspirin", "Vitamin complex", "Vitamin C");
    }

    @Test
    void findByActiveSubstanceByProductName() {
        session.beginTransaction();
        var filter = ProductFilter.builder()
                .name("Aspirin")
                .build();
        var predicate = QPredicate.builder()
                .add(filter.getName(), product.name::eq)
                .buildAnd();

        var result = new JPAQuery<ActiveSubstance>(session)
                .select(activeSubstance)
                .from(activeSubstance)
                .join(activeSubstance.productActiveSubstances, productActiveSubstance)
                .join(productActiveSubstance.product, product)
                .where(predicate).distinct()
                .fetch();

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(ActiveSubstance::getName)
                .toList();

        assertThat(actualResult).contains("Acetylsalicylic acid", "Ascorbic acid");
    }

    @Test
    void findProductsWherePriceBetween() {
        session.beginTransaction();
        var filter = ProductFilter.builder()
                .priceMin(30F)
                .priceMax(300F)
                .build();
        var predicate = QPredicate.builder()
                .addBetween(filter.getPriceMin(), filter.getPriceMax())
                .buildAnd();
        var result = new JPAQuery<Product>(session)
                .select(product)
                .from(product)
                .where(predicate)
                .orderBy(product.price.asc())
                .fetch();

        assertThat(result).hasSize(3);

        var actualResult = result.stream()
                .map(Product::getPrice)
                .toList();

        assertThat(actualResult).contains(45.00F, 125.00F, 56.20F);
        assertThat(actualResult.get(2)).isEqualTo(125.00F);
    }
}
