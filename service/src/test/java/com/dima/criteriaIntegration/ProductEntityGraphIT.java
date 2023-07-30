package com.dima.criteriaIntegration;

import com.dima.dto.ProductFilter;
import com.dima.entity.ActiveSubstance;
import com.dima.entity.Manufacturer;
import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance;
import com.dima.entity.Product_;
import com.dima.util.TestBase;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductEntityGraphIT extends TestBase {

    @Test
    void findProductsWithManufacturerInOneQuery() {
        ProductFilter filter = ProductFilter.builder()
                .name("Aspirin")
                .build();

        RootGraph<?> graph = session.createEntityGraph(Product.class);
        graph.addAttributeNodes("manufacturer");

        session.beginTransaction();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(product.get(Product_.name), filter.getName()));

        criteria.select(product).where(predicates.toArray(Predicate[]::new));

        var result = session.createQuery(criteria)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), graph)
                .list();

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .map(Product::getManufacturer)
                .map(Manufacturer::getName)
                .toList();

        assertThat(actualResult).contains("Pfizer", "Bayer");
    }

    @Test
    void findProductsWithActiveSubstanceInOneQuery() {
        ProductFilter filter = ProductFilter.builder()
                .name("Aspirin")
                .build();

        var productRootGraph = session.createEntityGraph(Product.class);
        productRootGraph.addAttributeNodes("productActiveSubstances");
        var productActiveSubstanceSubGraph = productRootGraph.addSubgraph(
                "productActiveSubstances", ProductActiveSubstance.class
        );
        productActiveSubstanceSubGraph.addAttributeNodes("activeSubstance");

        session.beginTransaction();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(product.get(Product_.name), filter.getName()));

        criteria.select(product).where(predicates.toArray(Predicate[]::new));

        var result = session.createQuery(criteria)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), productRootGraph)
                .list();

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .flatMap(p -> p.getProductActiveSubstances().stream())
                .map(ProductActiveSubstance::getActiveSubstance)
                .map(ActiveSubstance::getName)
                .toList();

        assertThat(actualResult).contains("Acetylsalicylic acid", "Ascorbic acid");
    }
}
