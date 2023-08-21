package com.dima.repository.filters;

import com.dima.dto.filters.ManufacturerFilter;
import com.dima.entity.Manufacturer;
import java.util.List;

public interface FilterManufacturerRepository {

    List<Manufacturer> findAllByFilter(ManufacturerFilter filter);
}
