package com.dima.dao;

import com.dima.dto.ProductFilter;
import com.dima.entity.ActiveSubstance_;
import com.dima.entity.Manufacturer_;
import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance_;
import com.dima.entity.ProductCategory_;
import com.dima.entity.Product_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class CriteriaPredicate {

    private static final List<Predicate> predicates = new ArrayList<>();
    private CriteriaBuilder cb;

    public static CriteriaPredicate builder(CriteriaBuilder cb) {
        CriteriaPredicate criteriaPredicate = new CriteriaPredicate();
        criteriaPredicate.cb = cb;
        return criteriaPredicate;
    }

    public <T> CriteriaPredicate add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

    public <K extends Collection<?>> CriteriaPredicate add(K collection, Function<K, Predicate> function) {
        if (!collection.isEmpty()) {
            predicates.add(function.apply(collection));
        }
        return this;
    }

    public <P extends Number & Comparable<? super P>> CriteriaPredicate addBetween(Path<P> path, P number1, P number2) {
        if (number1 != null && number2 != null) {
            if (number1.getClass() != number2.getClass()) {
                throw new IllegalArgumentException();
            }

            if (number1.compareTo(number2) > 0) {
                throw new IllegalArgumentException();
            }

            predicates.add(cb.between(path, number1, number2));
        }
        return this;
    }

    public Predicate buildAnd() {
        return cb.and(predicates.toArray(new Predicate[0]));
    }

    public Predicate buildOr() {
        return cb.or(predicates.toArray(new Predicate[0]));
    }


}
