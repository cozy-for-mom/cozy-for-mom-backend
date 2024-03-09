package com.juju.cozyformombackend3.global.validation.validator;

import java.util.Objects;

import com.juju.cozyformombackend3.domain.babylog.baby.model.Gender;
import com.juju.cozyformombackend3.global.validation.annotation.IsGenderType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderTypeValidator implements ConstraintValidator<IsGenderType, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Objects.nonNull(Gender.ofType(value));
    }
}
