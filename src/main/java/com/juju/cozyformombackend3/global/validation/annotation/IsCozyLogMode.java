package com.juju.cozyformombackend3.global.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.juju.cozyformombackend3.global.validation.validator.CozyLogModeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = CozyLogModeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsCozyLogMode {
    String message() default "올바르지 않은 코지로그 공개 모드입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

