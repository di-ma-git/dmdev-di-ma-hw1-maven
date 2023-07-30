package com.dima.repository;

import com.dima.entity.ProductCategory;

import javax.persistence.EntityManager;

public class ProductCategoryRepository extends RepositoryBase<Integer, ProductCategory> {

    public ProductCategoryRepository(EntityManager entityManager) {
        super(ProductCategory.class, entityManager);
    }

}
