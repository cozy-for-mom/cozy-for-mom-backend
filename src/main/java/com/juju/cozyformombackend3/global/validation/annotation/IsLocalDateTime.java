package com.juju.cozyformombackend3.global.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.juju.cozyformombackend3.global.validation.validator.LocalDateTimeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = LocalDateTimeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsLocalDateTime {
    String message() default "올바르지 않은 날짜 형식입니다. (yyyy-MM-dd HH:mm:ss)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
