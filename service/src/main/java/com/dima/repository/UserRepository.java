package com.dima.repository;

import com.dima.entity.User;
import com.dima.entity.User_;
import com.dima.enums.Role;

import javax.persistence.EntityManager;
import java.util.List;

public class UserRepository extends RepositoryBase<Long, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    public List<User> findUsersByRole(Role role) {

        var cb = getEntityManager().getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);

        criteria.select(user).where(cb.equal(user.get(User_.role), role));

        return entityManager.createQuery(criteria).getResultList();
    }

}
