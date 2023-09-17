package com.dima.repository.filters;

import com.dima.dao.CriteriaPredicate;
import com.dima.dto.filters.OrderFilter;
import com.dima.entity.Order;
import com.dima.entity.Order_;
import com.dima.entity.ProductInOrder_;
import com.dima.entity.User_;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FilterOrderRepositoryImpl implements FilterOrderRepository {

    private final EntityManager entityManager;

    @Override
    public List<Order> findAllByFilter(OrderFilter filter) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Order.class);
        var order = criteria.from(Order.class);
        var user = order.join(Order_.user);
        var productInOrder = order.join(Order_.productsInOrder);
        var product = productInOrder.join(ProductInOrder_.product);

        var predicateList = CriteriaPredicate.builder()
                .add(filter.getUserName(), value -> cb.equal(user.get(User_.name), value))
                .add(filter.getStatus(), value -> cb.equal(order.get(Order_.orderStatus), value))
                .build();
        var predicates = cb.and(predicateList.toArray(Predicate[]::new));

        criteria.select(order).where(predicates).distinct(true)
                .orderBy(cb.asc(order.get(Order_.orderDate)));

        return entityManager.createQuery(criteria).getResultList();
    }
}
