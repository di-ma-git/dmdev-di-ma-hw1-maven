package com.dima.repository;

import com.dima.entity.Manufacturer;

import com.dima.repository.filters.FilterManufacturerRepository;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer>, FilterManufacturerRepository {

    Optional<Manufacturer> findByName(String name);

    List<Manufacturer> findAllByCountry(String country);

    @Query("select m from Manufacturer m where m.name like %:fragment%")
    List<Manufacturer> findAllByNameFragment(String fragment);

    @EntityGraph(attributePaths = "products")
    @Query("select m from Manufacturer m join fetch m.products where m.name = :name")
    Optional<Manufacturer> findAllByNameWithProducts(String name);

}
