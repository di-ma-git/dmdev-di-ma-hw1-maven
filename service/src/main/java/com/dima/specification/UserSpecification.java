package com.dima.specification;

import com.dima.dao.CriteriaPredicate;
import com.dima.dto.filters.ProductFilter;
import com.dima.dto.filters.UserFilter;
import com.dima.entity.ActiveSubstance_;
import com.dima.entity.Manufacturer_;
import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance_;
import com.dima.entity.ProductCategory_;
import com.dima.entity.Product_;
import com.dima.entity.User;
import com.dima.entity.User_;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> withFilter(UserFilter filter) {
        return (user, query, cb) -> {
            var predicatesList = CriteriaPredicate.builder()
                    .add(filter.name(), value -> cb.like(cb.lower(user.get(User_.name)), "%" + value.toLowerCase() + "%"))
                    .add(filter.email(), value -> cb.like(cb.lower(user.get(User_.email)), "%" + value.toLowerCase() + "%"))
                    .add(filter.phoneNumber(), value -> cb.like(cb.lower(user.get(User_.phoneNumber)), "%" + value.toLowerCase() + "%"))
                    .add(filter.role(), value -> cb.equal(user.get(User_.role), value))
                    .build();

            return cb.and(predicatesList.toArray(Predicate[]::new));
        };
    }
}
