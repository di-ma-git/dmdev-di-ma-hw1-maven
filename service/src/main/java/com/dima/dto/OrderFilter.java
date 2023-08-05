package com.dima.dto;

import com.dima.enums.OrderStatus;
import com.dima.enums.Role;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderFilter {

    String userName;
    OrderStatus status;

}
