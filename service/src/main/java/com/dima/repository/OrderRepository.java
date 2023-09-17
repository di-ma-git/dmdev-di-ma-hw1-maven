package com.dima.repository;

import com.dima.entity.Order;
import com.dima.entity.User;
import com.dima.repository.filters.FilterOrderRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends
        JpaRepository<Order, Long>,
        FilterOrderRepository,
        JpaSpecificationExecutor<Order> {

    List<Order> findAllByUser(User user);

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {"productsInOrder.product"})
    Optional<Order> findById(Long id);
}
