package com.dima.repository;

import com.dima.entity.Order;
import com.dima.entity.Product;
import com.dima.entity.User;
import com.dima.enums.OrderStatus;
import com.dima.repository.filters.FilterOrderRepository;
import java.time.LocalDateTime;
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
    List<Order> findAllByUserAndOrderDate(User user, LocalDateTime date);
    List<Order> findAllByUserAndOrderDateAndAndOrderStatus(User user, LocalDateTime date, OrderStatus status);
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {"productsInOrder.product"})
    Optional<Order> findById(Long id);
}
