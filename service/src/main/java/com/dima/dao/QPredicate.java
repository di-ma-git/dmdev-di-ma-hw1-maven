package com.dima.dao;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static com.dima.entity.QProduct.product;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QPredicate {

    private final List<Predicate> predicates = new ArrayList<>();

    public static QPredicate builder() {
        return new QPredicate();
    }

    public <T> QPredicate add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

    public <K extends Collection> QPredicate add(K collection, Function<K, Predicate> function) {
        if (!collection.isEmpty()) {
            predicates.add(function.apply(collection));
        }
        return this;
    }

    public <P extends Number> QPredicate addBetween(P priceMin, P priceMax) {
        if (priceMin != null && priceMax != null) {
            predicates.add(product.price.between((Float) priceMin, (Float) priceMax));
        }
        return this;
    }

    public Predicate buildAnd() {
        return ExpressionUtils.allOf(predicates);
    }
    public Predicate buildOr() {
        return ExpressionUtils.anyOf(predicates);
    }

}
