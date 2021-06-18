/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.auth.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckCurrencyCode implements ConstraintValidator<CurrencyCode, String> {
    @Override
    public void initialize(CurrencyCode constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return NeesLocaleUtil.isValidCurrencyCode(value);
    }
}
