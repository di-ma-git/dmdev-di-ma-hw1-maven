package com.dima.repository;

import com.dima.entity.ActiveSubstance;

import com.dima.repository.filters.FilterActiveSubstanceRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveSubstanceRepository extends
        JpaRepository<ActiveSubstance, Integer>,
        FilterActiveSubstanceRepository {

}
