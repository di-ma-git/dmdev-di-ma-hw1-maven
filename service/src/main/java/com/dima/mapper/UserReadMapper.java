package com.dima.mapper;

import com.dima.dto.UserReadDto;
import com.dima.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {
    @Override
    public UserReadDto map(User object) {
        return UserReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .email(object.getEmail())
                .phoneNumber(object.getPhoneNumber())
                .role(object.getRole())
                .deliveryAddress(object.getDeliveryAddress())
                .image(object.getImage())
                .build();


    }
}
