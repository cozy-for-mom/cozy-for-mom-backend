package com.juju.cozyformombackend3.global.validation.validator;

import java.util.Objects;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogMode;
import com.juju.cozyformombackend3.global.validation.annotation.IsCozyLogMode;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CozyLogModeValidator implements ConstraintValidator<IsCozyLogMode, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Objects.nonNull(CozyLogMode.ofType(value));
    }
}
