package com.dima.repository;

import com.dima.entity.Product;
import com.dima.entity.ProductCategory;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

}
