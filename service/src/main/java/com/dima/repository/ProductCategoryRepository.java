package com.dima.repository;

import com.dima.entity.ProductCategory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

}
