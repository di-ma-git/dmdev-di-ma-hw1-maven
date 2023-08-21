package com.dima.repository.filters;

import com.dima.dto.filters.ActiveSubstanceFilter;
import com.dima.dto.filters.OrderFilter;
import com.dima.entity.ActiveSubstance;
import com.dima.entity.Order;
import java.util.List;

public interface FilterOrderRepository {

    List<Order> findAllByFilter(OrderFilter filter);
}
