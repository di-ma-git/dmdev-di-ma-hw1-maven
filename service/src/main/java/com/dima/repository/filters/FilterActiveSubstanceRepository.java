package com.dima.repository.filters;

import com.dima.dto.filters.ActiveSubstanceFilter;
import com.dima.entity.ActiveSubstance;
import java.util.List;

public interface FilterActiveSubstanceRepository {

    List<ActiveSubstance> findAllByFilter(ActiveSubstanceFilter filter);
}
