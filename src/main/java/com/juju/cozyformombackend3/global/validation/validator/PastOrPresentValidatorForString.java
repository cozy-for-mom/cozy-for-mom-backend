package com.juju.cozyformombackend3.global.validation.validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.juju.cozyformombackend3.global.validation.annotation.IsPastOrPresent;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PastOrPresentValidatorForString implements ConstraintValidator<IsPastOrPresent, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        LocalDateTime datetime = null;
        try {
            datetime = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            return false;
        }

        return !datetime.isAfter(LocalDateTime.now());
    }
}
