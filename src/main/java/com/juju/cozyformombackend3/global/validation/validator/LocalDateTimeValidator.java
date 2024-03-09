package com.juju.cozyformombackend3.global.validation.validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.juju.cozyformombackend3.global.validation.annotation.IsLocalDateTime;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LocalDateTimeValidator implements ConstraintValidator<IsLocalDateTime, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
