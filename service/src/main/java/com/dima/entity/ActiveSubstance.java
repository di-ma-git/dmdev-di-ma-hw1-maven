package com.dima.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "productActiveSubstances")
@ToString(exclude = "productActiveSubstances")
@Builder
@Entity
public class ActiveSubstance implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;
    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "activeSubstance", cascade = CascadeType.ALL)
    private List<ProductActiveSubstance> productActiveSubstances = new ArrayList<>();

}
