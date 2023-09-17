package com.dima.validation;

import com.dima.dto.UserCreateDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class UserRegistrationInfoValidator implements ConstraintValidator<UserRegistrationInfo, UserCreateDto> {

    @Override
    public void initialize(UserRegistrationInfo constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserCreateDto value, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasText(value.getEmail()) || StringUtils.hasText(value.getPhoneNumber());
    }
}
