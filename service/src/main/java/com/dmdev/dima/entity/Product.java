package com.dmdev.dima.entity;

import com.dmdev.dima.entity.enums.MedicineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"manufacturer", "productActiveSubstances", "productCategory"})
@ToString(exclude = {"manufacturer", "productActiveSubstances", "productCategory"})
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private Float price;
    private Long quantityPerPackaging;
    @Column(name = "quantity_active_substance_per_one_dose")
    private Double quantityPerDose;
    private String description;
    @Enumerated(EnumType.STRING)
    private MedicineType medicineType;
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductCategory productCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    private Manufacturer manufacturer;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductActiveSubstance> productActiveSubstances = new ArrayList<>();
}
