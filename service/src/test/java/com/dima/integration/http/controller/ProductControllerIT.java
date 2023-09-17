package com.dima.integration.http.controller;

import com.dima.repository.ManufacturerRepository;
import com.dima.repository.ProductCategoryRepository;
import com.dima.repository.ProductRepository;
import com.dima.testdata.TestSimpleData;
import com.dima.util.TestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.dima.dto.ProductCreateDto.Fields.description;
import static com.dima.dto.ProductCreateDto.Fields.manufacturerId;
import static com.dima.dto.ProductCreateDto.Fields.name;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;


@AutoConfigureMockMvc
@RequiredArgsConstructor
public class ProductControllerIT extends TestBase {

    private final MockMvc mockMvc;
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("product/products"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasSize(6)));
    }

    @Test
    void findById() throws Exception {
        var manufacturer = manufacturerRepository.save(TestSimpleData.getSimpleTestManufacturer());
        var productCategory = productCategoryRepository.save(TestSimpleData.getSimpleTestProductCategory());
        var product = TestSimpleData.getSimpleTestProduct();
        product.setManufacturer(manufacturer);
        product.setProductCategory(productCategory);
        productRepository.save(product);

        mockMvc.perform(get("/products/" + product.getId()))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("product/product"),
                        model().attributeExists("product"),
                        model().attribute("product", hasProperty("name", equalTo("Aspirine")))
                );
    }

    @Test
    void findById2() throws Exception {
        var product = productRepository.findByName("Testosterone").get();

        mockMvc.perform(get("/products/" + product.getId()))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("product/product"),
                        model().attributeExists("product"),
                        model().attribute("product", hasProperty("name", equalTo("Testosterone")))
                );
    }

    @Test
    void findByIdFailedIfProductIsNotExist() throws Exception {
        mockMvc.perform(get("/products/50000"))
                .andExpect(view().name("error/error404"));
    }

    @Test
    void create() throws Exception {
        var manufacturer = manufacturerRepository.findByName("Bayer").get();

        mockMvc.perform(post("/products")
                .param(name, "Some product")
                .param(description,"Some description")
                .param(manufacturerId, manufacturer.getId().toString()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/products/{\\d+}")
                );
    }
}
