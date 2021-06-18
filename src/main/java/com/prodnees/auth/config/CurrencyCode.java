/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.auth.config;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Checks if a String is a valid Currency code.
 * <i>{@link NeesLocaleUtil#isValidCurrencyCode(String)}</i>
 * <p>Null Value is considered valid</p>
 */
@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckCurrencyCode.class)
public @interface CurrencyCode {


    String message() default "Invalid Currency Code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
