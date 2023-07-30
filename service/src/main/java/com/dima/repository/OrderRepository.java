package com.dima.repository;

import com.dima.dao.CriteriaPredicate;
import com.dima.dto.OrderFilter;
import com.dima.entity.Order;
import com.dima.entity.Order_;
import com.dima.entity.ProductInOrder_;
import com.dima.entity.User_;

import javax.persistence.EntityManager;
import java.util.List;

public class OrderRepository extends RepositoryBase<Long, Order> {

    private OrderFilter filter;

    public OrderRepository(EntityManager entityManager) {
        super(Order.class, entityManager);
    }

    public List<Order> findOrdersByFilter(OrderFilter filter) {
        var cb = getEntityManager().getCriteriaBuilder();
        var criteria = cb.createQuery(Order.class);
        var order = criteria.from(Order.class);
        var user = order.join(Order_.user);
        var productInOrder = order.join(Order_.productsInOrder);
        var product = productInOrder.join(ProductInOrder_.product);

        var predicates = CriteriaPredicate.builder(cb)
                .add(filter.getStatus(), value -> cb.equal(order.get(Order_.orderStatus), value))
                .add(filter.getUserName(), value -> cb.equal(user.get(User_.name), value))
                .buildAnd();

        criteria.select(order).where(predicates).distinct(true)
                .orderBy(cb.asc(order.get(Order_.orderDate)));

        return getEntityManager().createQuery(criteria).getResultList();


    }

}
