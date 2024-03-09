package com.juju.cozyformombackend3.global.validation.validator;

import java.util.Objects;

import com.juju.cozyformombackend3.domain.userlog.meal.model.MealType;
import com.juju.cozyformombackend3.global.validation.annotation.IsMealRecordType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MealRecordTypeValidator implements ConstraintValidator<IsMealRecordType, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Objects.nonNull(MealType.ofType(value));
    }
}
