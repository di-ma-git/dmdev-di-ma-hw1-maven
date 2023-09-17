package com.dima.repository.filters;

import com.dima.dao.CriteriaPredicate;
import com.dima.dto.filters.UserFilter;
import com.dima.entity.User;
import com.dima.entity.User_;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private final EntityManager entityManager;

    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);

        var predicatesList = CriteriaPredicate.builder()
                .add(filter.name(), value -> cb.like(cb.lower(user.get(User_.name)), "%" + value.toLowerCase() + "%"))
                .add(filter.email(), value -> cb.like(cb.lower(user.get(User_.email)), "%" + value.toLowerCase() + "%"))
                .add(filter.phoneNumber(), value -> cb.like(cb.lower(user.get(User_.phoneNumber)), "%" + value.toLowerCase() + "%"))
                .add(filter.role(), value -> cb.equal(user.get(User_.role), value))
                .build();
        var predicates = cb.and(predicatesList.toArray(Predicate[]::new));

        criteria.select(user).where(predicates);

        return entityManager.createQuery(criteria).getResultList();
    }
}
