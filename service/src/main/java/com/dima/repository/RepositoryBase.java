package com.dima.repository;

import com.dima.entity.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class RepositoryBase<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {

    private final Class<E> clazz;

    @Getter
    final EntityManager entityManager;

    @Override
    public Optional<E> findById(K id, Map<String, Object> properties) {
        return Optional.ofNullable(entityManager.find(clazz, id, properties));
    }

    @Override
    public E save(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void update(E entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(E entity) {
        entityManager.remove(entityManager.find(clazz, entity.getId()));
        entityManager.flush();
    }

    @Override
    public List<E> findAll() {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(clazz);
        criteria.from(clazz);

        return entityManager.createQuery(criteria).getResultList();
    }
}
