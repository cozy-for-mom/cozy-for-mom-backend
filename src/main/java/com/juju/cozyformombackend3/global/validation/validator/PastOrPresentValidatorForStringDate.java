package com.juju.cozyformombackend3.global.validation.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.juju.cozyformombackend3.global.validation.annotation.IsPastOrPresentDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PastOrPresentValidatorForStringDate implements ConstraintValidator<IsPastOrPresentDate, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        LocalDate date = null;
        try {
            date = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            return false;
        }

        if (date.isAfter(LocalDate.now())) {
            return false;
        }
        return true;
    }
}
