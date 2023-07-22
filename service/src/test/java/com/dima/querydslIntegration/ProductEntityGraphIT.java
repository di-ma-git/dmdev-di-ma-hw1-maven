package com.dima.querydslIntegration;

import com.dima.dao.QPredicate;
import com.dima.dto.ProductFilter;
import com.dima.entity.ActiveSubstance;
import com.dima.entity.Manufacturer;
import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance;
import com.dima.util.TestBase;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.graph.SubGraph;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;

import static com.dima.entity.QManufacturer.manufacturer;
import static com.dima.entity.QProduct.product;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductEntityGraphIT extends TestBase {

    @Test
    void findProductsWithManufacturerInOneQuery() {
        ProductFilter filter = ProductFilter.builder()
                .name("Aspirin")
                .build();

        session.beginTransaction();

        RootGraph<?> graph = session.createEntityGraph(Product.class);
        graph.addAttributeNodes("manufacturer");

        var predicate = QPredicate.builder()
                .add(filter.getName(), product.name::eq)
                .buildAnd();
        var result = new JPAQuery<Product>(session).setHint(GraphSemantic.LOAD.getJpaHintName(), graph)
                .select(product)
                .from(product)
                .where(predicate)
                .fetch();

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

        session.beginTransaction();
        var productRootGraph = session.createEntityGraph(Product.class);
        productRootGraph.addAttributeNodes("productActiveSubstances");
        var productActiveSubstanceSubGraph = productRootGraph.addSubgraph(
                "productActiveSubstances", ProductActiveSubstance.class
        );
        productActiveSubstanceSubGraph.addAttributeNodes("activeSubstance");

        var predicate = QPredicate.builder()
                .add(filter.getName(), product.name::eq)
                .buildAnd();
        var result = new JPAQuery<Product>(session).setHint(GraphSemantic.LOAD.getJpaHintName(), productRootGraph)
                .select(product)
                .from(product)
                .where(predicate)
                .fetch();

        assertThat(result).hasSize(2);

        var actualResult = result.stream()
                .flatMap(product -> product.getProductActiveSubstances().stream())
                .map(ProductActiveSubstance::getActiveSubstance)
                .map(ActiveSubstance::getName)
                .toList();

        assertThat(actualResult).contains("Acetylsalicylic acid", "Ascorbic acid");
    }


}
