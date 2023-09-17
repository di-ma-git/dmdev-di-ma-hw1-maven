package com.dima.mapper;

import com.dima.dto.UserCreateDto;
import com.dima.entity.User;
import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UserCreateMapper implements Mapper<UserCreateDto, User> {

    @Override
    public User map(UserCreateDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateDto object) {
        User user = new User();
        copy(object, user);
        return user;
    }

    private static void copy(UserCreateDto object, User user) {
        user.setName(object.getName());
        user.setEmail(object.getEmail());
        user.setPhoneNumber(object.getPhoneNumber());
        user.setRole(object.getRole());
        user.setDeliveryAddress(object.getDeliveryAddress());

        // TODO        user.setPassword();

        Optional.ofNullable(object.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> user.setImage(image.getOriginalFilename()));
    }
}
