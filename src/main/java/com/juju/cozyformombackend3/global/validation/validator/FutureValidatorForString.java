package com.juju.cozyformombackend3.global.validation.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.juju.cozyformombackend3.global.validation.annotation.IsFuture;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FutureValidatorForString implements ConstraintValidator<IsFuture, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        LocalDate date = null;
        try {
            date = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            return false;
        }

        return !date.isBefore(LocalDate.now());
    }
}
