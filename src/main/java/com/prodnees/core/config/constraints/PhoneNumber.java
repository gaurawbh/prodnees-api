/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.core.config.constraints;


import com.prodnees.core.config.constraints.check.CheckPhoneNumber;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Checks if a String is a valid phone number.
 * <p>Null Value is considered valid</p>
 */
@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckPhoneNumber.class)
public @interface PhoneNumber {

    String message() default "can only contain numbers and '+'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
