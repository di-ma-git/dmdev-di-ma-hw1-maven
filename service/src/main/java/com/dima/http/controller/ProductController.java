package com.dima.http.controller;

import com.dima.dto.ProductCreateDto;
import com.dima.enums.MedicineType;
import com.dima.service.ActiveSubstanceService;
import com.dima.service.ManufacturerService;
import com.dima.service.ProductCategoryService;
import com.dima.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final ProductCategoryService productCategoryService;
    private final ActiveSubstanceService activeSubstanceService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/products";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        return productService.findById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    model.addAttribute("medicineTypes", MedicineType.values());
                    model.addAttribute("manufacturers", manufacturerService.findAll());
                    model.addAttribute("productCategories", productCategoryService.findAll());
                    model.addAttribute("activeSubstances", activeSubstanceService.findAll());
                    return "product/product";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(@ModelAttribute @Validated ProductCreateDto productDto) {
        return "redirect:/products/" + productService.create(productDto).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute ProductCreateDto productDto) {
        return productService.update(id, productDto)
                .map(it -> "redirect:/products/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        if (!productService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/products";
    }




}
