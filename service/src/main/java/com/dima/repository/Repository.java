package com.dima.repository;

import com.dima.entity.BaseEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.*;

public interface Repository<T extends Serializable, E extends BaseEntity<T>> {

    default Optional<E> findById(T id) {
        return findById(id, emptyMap());
    };
    Optional<E> findById(T id, Map<String, Object> properties);

    E save(E entity);

    void update(E entity);

    void delete(E entity);

    List<E> findAll();

}
