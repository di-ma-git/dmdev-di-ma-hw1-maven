package com.dima.repository;

import com.dima.entity.Product;
import com.dima.repository.filters.FilterProductRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends
        JpaRepository<Product, Long>,
        FilterProductRepository,
        JpaSpecificationExecutor<Product> {

    Optional<Product> findByName(String name);

    Page<Product> findAllByProductCategoryName(String name, Pageable pageable);
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {"manufacturer", "productActiveSubstances.activeSubstance"})
    Page<Product> findAllBy(Pageable pageable);
}
