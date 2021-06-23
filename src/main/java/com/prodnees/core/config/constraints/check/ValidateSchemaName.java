package com.prodnees.core.config.constraints.check;

import com.prodnees.core.config.constraints.SchemaName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateSchemaName implements ConstraintValidator<SchemaName, String> {
    private static final String VALID_SCHEMA_PATTERN = "^[a-zA-Z0-9_]+$";

    @Override
    public void initialize(SchemaName constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.matches(VALID_SCHEMA_PATTERN);
    }
}
