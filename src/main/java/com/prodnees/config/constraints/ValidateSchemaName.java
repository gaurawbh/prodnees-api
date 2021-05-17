package com.prodnees.config.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateSchemaName implements ConstraintValidator<SchemaName, String> {
    private static final String ALPHA_NUMERIC = "^[a-zA-Z0-9_]+$";

    @Override
    public void initialize(SchemaName constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.matches(ALPHA_NUMERIC);
    }
}
