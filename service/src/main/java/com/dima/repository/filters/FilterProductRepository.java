package com.dima.repository.filters;

import com.dima.dto.filters.ProductFilter;
import com.dima.entity.Product;
import java.util.List;

public interface FilterProductRepository {

    List<Product> findAllByFilter(ProductFilter filter);


}
