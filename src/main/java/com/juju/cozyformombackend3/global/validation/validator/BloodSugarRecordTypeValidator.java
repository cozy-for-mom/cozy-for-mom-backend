package com.juju.cozyformombackend3.global.validation.validator;

import java.util.Objects;

import com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.BloodSugarRecordType;
import com.juju.cozyformombackend3.global.validation.annotation.IsBloodSugarRecordType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BloodSugarRecordTypeValidator implements ConstraintValidator<IsBloodSugarRecordType, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Objects.nonNull(BloodSugarRecordType.ofDescription(value));
    }
}
