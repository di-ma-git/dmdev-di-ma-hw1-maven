package com.dima.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor@NoArgsConstructor
@EqualsAndHashCode(exclude = {"activeSubstance", "product"})
@ToString(exclude = {"activeSubstance", "product"})
@Builder
@Entity
public class ProductActiveSubstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Product product;
    @ManyToOne
    private ActiveSubstance activeSubstance;

    public void setProduct(Product product) {
        this.product = product;
        this.product.getProductActiveSubstances().add(this);
    }
    public void setActiveSubstance(ActiveSubstance activeSubstance) {
        this.activeSubstance = activeSubstance;
        this.activeSubstance.getProductActiveSubstances().add(this);
    }


}
