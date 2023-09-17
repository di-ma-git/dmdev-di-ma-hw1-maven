package com.dima.repository;

import com.dima.entity.Product;
import com.dima.entity.User;
import com.dima.repository.filters.FilterProductRepository;
import com.dima.repository.filters.FilterUserRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface ProductRepository extends
        JpaRepository<Product, Long>,
        FilterProductRepository,
        JpaSpecificationExecutor<Product> {
    Page<Product> findAllByProductCategoryName(String name, Pageable pageable);
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {"manufacturer", "productActiveSubstances.activeSubstance"})
    Page<Product> findAllBy(Pageable pageable);
}
