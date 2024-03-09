package com.juju.cozyformombackend3.global.validation.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.juju.cozyformombackend3.global.validation.annotation.IsLocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LocalDateValidator implements ConstraintValidator<IsLocalDate, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
