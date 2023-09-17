package com.dima.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;

@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "The password must contain at least one digit, an upper case letter and a special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
