package com.dima.entity;

import com.dima.enums.MedicineType;
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
import javax.persistence.JoinColumn;
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
public class Product implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private Float price;
    private Integer quantityPerPackaging;
    @Column(name = "quantity_active_substance_per_one_dose")
    private Double quantityPerDose;
    private String description;
    @Enumerated(EnumType.STRING)
    private MedicineType medicineType;
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductCategory productCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @Builder.Default
    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProductActiveSubstance> productActiveSubstances = new ArrayList<>();

    public void addProductActiveSubstance(ProductActiveSubstance productActiveSubstance) {
        productActiveSubstances.add(productActiveSubstance);
        productActiveSubstance.setProduct(this);
    }

    public void addProductActiveSubstance(List<ProductActiveSubstance> pas) {
//        productActiveSubstances.addAll(productActiveSubstances);
        for (ProductActiveSubstance productActiveSubstance : pas) {
            productActiveSubstances.add(productActiveSubstance);
            productActiveSubstance.setProduct(this);
        }
    }

}
