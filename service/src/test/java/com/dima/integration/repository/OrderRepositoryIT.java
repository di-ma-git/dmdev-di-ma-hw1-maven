package com.dima.integration.repository;

import com.dima.dto.filters.OrderFilter;
import com.dima.entity.Order;
import com.dima.entity.Product;
import com.dima.entity.ProductInOrder;
import com.dima.enums.OrderStatus;
import com.dima.repository.OrderRepository;
import com.dima.repository.UserRepository;
import com.dima.specification.OrderSpecification;
import com.dima.specification.ProductSpecification;
import com.dima.util.TestBase;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class OrderRepositoryIT extends TestBase {

    private final OrderRepository orderRepository;

    @Test
    void findOrdersByUsernameAndOrderStatusPageable() {
        var sortBy = Sort.sort(Order.class);
        var sort = sortBy.by(Order::getOrderDate);

        var pageable = PageRequest.of(0, 2, sort);

        var filter = OrderFilter.builder()
                .userName("User2")
                .status(OrderStatus.PAID)
                .build();

        var specification = OrderSpecification.withFilter(filter);

        var result = orderRepository.findAll(specification, pageable);

        assertThat(result).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getTotalPages()).isEqualTo(1);

        var actualResult = result.stream()
                .toList();

        assertThat(actualResult.get(0).getOrderStatus()).isEqualTo(OrderStatus.PAID);
        assertThat(actualResult.get(1).getOrderDate()).isEqualTo(of(2023, 1, 10, 11, 0));
    }

    @Test
    void findAllProductsFromOrder() {
        var result = orderRepository.findById(1L).get();

        var actualResult = result.getProductsInOrder().stream()
                .map(ProductInOrder::getProduct)
                .toList()
                .stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Aspirin", "Testosterone", "Vitamin C");
    }
}
