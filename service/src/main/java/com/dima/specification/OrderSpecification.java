package com.dima.specification;

import com.dima.dao.CriteriaPredicate;
import com.dima.dto.filters.OrderFilter;
import com.dima.dto.filters.ProductFilter;
import com.dima.entity.ActiveSubstance_;
import com.dima.entity.Manufacturer_;
import com.dima.entity.Order;
import com.dima.entity.Order_;
import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance_;
import com.dima.entity.ProductCategory_;
import com.dima.entity.Product_;
import com.dima.entity.User_;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {

    public static Specification<Order> withFilter(OrderFilter filter) {
        return (order, query, cb) -> {
            var predicateList = CriteriaPredicate.builder()
                    .add(filter.getUserName(), value -> cb.equal(order.join(Order_.user).get(User_.name), value))
                    .add(filter.getStatus(), value -> cb.equal(order.get(Order_.orderStatus), value))
                    .build();

            return cb.and(predicateList.toArray(Predicate[]::new));
        };
    }
}
