package com.dima.repository;

import com.dima.entity.ProductActiveSubstance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface ProductActiveSubstanceRepository extends JpaRepository<ProductActiveSubstance, Long> {

    public List<ProductActiveSubstance> findAllByProductId(Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    public void deleteAllByProductId(Long id);
}
