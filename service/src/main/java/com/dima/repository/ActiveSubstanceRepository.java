package com.dima.repository;

import com.dima.dao.CriteriaPredicate;
import com.dima.dto.filters.ActiveSubstanceFilter;
import com.dima.entity.ActiveSubstance;
import com.dima.entity.ActiveSubstance_;
import com.dima.entity.Manufacturer_;
import com.dima.entity.ProductActiveSubstance_;
import com.dima.entity.Product_;

import com.dima.repository.filters.FilterActiveSubstanceRepository;
import com.dima.repository.filters.FilterActiveSubstanceRepositoryImpl;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ActiveSubstanceRepository extends
        JpaRepository<ActiveSubstance, Integer>,
        FilterActiveSubstanceRepository {

}
