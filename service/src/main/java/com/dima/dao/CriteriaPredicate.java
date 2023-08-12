package com.dima.dao;

import java.util.function.BiFunction;
import java.util.function.Supplier;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class CriteriaPredicate {

    private final List<Predicate> predicates = new ArrayList<>();

    public static CriteriaPredicate builder() {
        return new CriteriaPredicate();
    }

    public <T> CriteriaPredicate add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

    public <K extends Collection<?>> CriteriaPredicate add(K collection, Function<K, Predicate> function) {
        if (collection != null && !collection.isEmpty()) {
                predicates.add(function.apply(collection));
        }
        return this;
    }

    public <P extends Number & Comparable<? super P>> CriteriaPredicate add(P number1, P number2, BiFunction<P, P, Predicate> function) {
        if (number1 != null && number2 != null) {
            if (number1.compareTo(number2) > 0) {
                throw new IllegalArgumentException();
            }
            predicates.add(function.apply(number1, number2));
        }
        return this;
    }

    public CriteriaPredicate add(Supplier<Boolean> condition, Supplier<Predicate> predicate) {
        if (condition.get()) { predicates.add(predicate.get()); }
        return this;
    }

    public List<Predicate> build() {
        return predicates;
    }



}
