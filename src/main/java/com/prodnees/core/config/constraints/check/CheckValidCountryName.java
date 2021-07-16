/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.core.config.constraints.check;

import com.prodnees.auth.config.NeesLocaleUtil;
import com.prodnees.core.config.constraints.ValidCountryName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckValidCountryName implements ConstraintValidator<ValidCountryName, String> {
    @Override
    public void initialize(ValidCountryName constraint) {
    }

    public boolean isValid(String obj, ConstraintValidatorContext context) {
        return NeesLocaleUtil.isValidCountryName(obj);
    }
}
