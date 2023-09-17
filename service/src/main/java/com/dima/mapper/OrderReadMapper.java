package com.dima.mapper;

import com.dima.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderReadMapper implements Mapper<Order, OrderReadMapper> {

    @Override
    public OrderReadMapper map(Order object) {
        return null;
    }
}
