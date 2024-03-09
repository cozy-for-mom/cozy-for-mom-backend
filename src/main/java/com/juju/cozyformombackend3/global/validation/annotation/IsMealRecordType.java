package com.juju.cozyformombackend3.global.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.juju.cozyformombackend3.global.validation.validator.MealRecordTypeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = MealRecordTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsMealRecordType {
    String message() default "올바르지 않은 식사 기록 타입입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
