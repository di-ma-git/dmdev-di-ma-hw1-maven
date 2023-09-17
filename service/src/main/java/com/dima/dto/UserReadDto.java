package com.dima.dto;

import com.dima.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class UserReadDto {

    Long id;
    String name;
    Role role;
    String email;
    String phoneNumber;
    String deliveryAddress;
    String image;
}
