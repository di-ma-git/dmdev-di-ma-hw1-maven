package com.dmdev.dima.entity;

import com.dmdev.dima.entity.enums.MedicineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "product_image")
    private String image;
    @Column(name = "product_price")
    private Float price;
    @Column(name = "product_quantity_per_packaging")
    private Long quantityPerPackaging;
    @Column(name = "product_active_substance")
    private Long activeSubstance;
    @Column(name = "product_quantity_active_substance_per_one_dose")
    private Double quantityPerDose;
    @Column(name = "product_description")
    private String description;
    @Column(name = "product_manufacturer")
    private Long manufacturer;
    @Column(name = "product_category")
    private Long category;
    @Column(name = "product_medicine_type")
    private MedicineType medicineType;




}
