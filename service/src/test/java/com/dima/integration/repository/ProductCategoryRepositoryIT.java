package com.dima.integration.repository;

import com.dima.entity.Product;
import com.dima.repository.ProductCategoryRepository;
import com.dima.repository.ProductRepository;
import com.dima.testdata.TestSimpleData;
import com.dima.util.TestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ProductCategoryRepositoryIT extends TestBase {

    private final ProductCategoryRepository productCategoryRepository;

}
