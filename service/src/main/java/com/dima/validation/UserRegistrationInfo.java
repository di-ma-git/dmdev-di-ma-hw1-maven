package com.dima.validation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UserRegistrationInfoValidator.class)
public @interface UserRegistrationInfo {

    String message() default "Email or phone number should be filled in";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
