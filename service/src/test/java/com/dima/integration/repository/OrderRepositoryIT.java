package com.dima.integration.repository;

import com.dima.dto.OrderFilter;
import com.dima.entity.Order;
import com.dima.entity.Product;
import com.dima.entity.ProductInOrder;
import com.dima.entity.User;
import com.dima.enums.OrderStatus;
import com.dima.enums.Role;
import com.dima.repository.OrderRepository;
import com.dima.repository.UserRepository;
import com.dima.util.TestBase;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;


public class OrderRepositoryIT extends TestBase {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderRepositoryIT(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Test
    void saveOrderSuccessful() {
        var user = User.builder()
                .name("someuser2")
                .email("test2@gmail.com")
                .password("12345")
                .phoneNumber("+79876543211")
                .role(Role.CUSTOMER)
                .build();
        var order = Order.builder()
                .orderStatus(OrderStatus.NOT_PAID)
                .orderDate(LocalDateTime.now())
                .user(user)
                .build();

        userRepository.save(user);
        orderRepository.save(order);
        entityManager.flush();
        entityManager.detach(order);

        assertThat(order.getId()).isNotNull();
    }

    @Test
    void findOrderSuccessful() {
        var user = User.builder()
                .name("someuser2")
                .email("test2@gmail.com")
                .password("12345")
                .phoneNumber("+79876543211")
                .role(Role.CUSTOMER)
                .build();
        var order = Order.builder()
                .orderStatus(OrderStatus.NOT_PAID)
                .orderDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user)
                .build();

        userRepository.save(user);
        orderRepository.save(order);
        entityManager.flush();
        entityManager.clear();

        var actualResult = orderRepository.findById(order.getId()).get();

        assertThat(actualResult).isEqualTo(order);
        assertThat(actualResult.getUser()).isEqualTo(user);
    }

    @Test
    void updateOrderSuccessful() {
        var user = User.builder()
                .name("someuser2")
                .email("test2@gmail.com")
                .password("12345")
                .phoneNumber("+79876543211")
                .role(Role.CUSTOMER)
                .build();
        var order = Order.builder()
                .orderStatus(OrderStatus.NOT_PAID)
                .orderDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user)
                .build();

        userRepository.save(user);
        orderRepository.save(order);
        order.setOrderStatus(OrderStatus.PAID);
        entityManager.flush();
        entityManager.clear();

        var actualResult = orderRepository.findById(order.getId()).get();

        assertThat(actualResult.getOrderStatus()).isEqualTo(OrderStatus.PAID);
    }

    @Test
    void deleteOrderButNotDeleteUserSuccessful() {
        var user = User.builder()
                .name("someuser2")
                .email("test2@gmail.com")
                .password("12345")
                .phoneNumber("+79876543211")
                .role(Role.CUSTOMER)
                .build();
        var order = Order.builder()
                .orderStatus(OrderStatus.NOT_PAID)
                .orderDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user)
                .build();

        userRepository.save(user);
        orderRepository.save(order);
        entityManager.flush();
        entityManager.clear();
        orderRepository.delete(order);
        entityManager.flush();
        entityManager.clear();

        var actualOrderResult = orderRepository.findById(order.getId());
        var actualUserResult = userRepository.findById(user.getId()).get();

        assertThat(actualOrderResult).isEmpty();
        assertThat(actualUserResult).isNotNull();
        assertThat(actualUserResult.getOrders()).isEmpty();
    }


    @Test
    void findOrdersByUsernameAndOrderStatus() {

        var filter = OrderFilter.builder()
                .userName("User2")
                .status(OrderStatus.PAID)
                .build();

        var result = orderRepository.findOrdersByFilter(filter);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getOrderStatus()).isEqualTo(OrderStatus.PAID);
        assertThat(result.get(1).getOrderDate()).isEqualTo(of(2023, 1, 10, 11, 0));

    }

    @Test
    void findAllProductsFromOrder() {

        var orderRootGraph = entityManager.createEntityGraph(Order.class);
        orderRootGraph.addAttributeNodes("productsInOrder");
        var productInOrderSubGraph = orderRootGraph.addSubgraph("productsInOrder", ProductInOrder.class);
        productInOrderSubGraph.addAttributeNodes("product");

        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJpaHintName(), orderRootGraph
        );

        var all = orderRepository.findAll();

        var result = orderRepository.findById(all.get(0).getId(), properties).get();


        var actualResult = result.getProductsInOrder().stream()
                .map(ProductInOrder::getProduct)
                .toList()
                .stream()
                .map(Product::getName)
                .toList();

        assertThat(actualResult).contains("Aspirin", "Testosterone", "Vitamin C");
    }


}
