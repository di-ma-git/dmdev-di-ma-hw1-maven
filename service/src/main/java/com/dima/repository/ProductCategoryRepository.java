package com.dima.repository;

import com.dima.entity.ProductCategory;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ProductCategoryRepository extends RepositoryBase<Integer, ProductCategory> {

    public ProductCategoryRepository(EntityManager entityManager) {
        super(ProductCategory.class, entityManager);
    }

}
