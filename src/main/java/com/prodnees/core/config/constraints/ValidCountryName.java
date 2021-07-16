/*
 * Copyright (c) This is the property fo Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing to will be prosecuted by Gauri Baba.
 */

package com.prodnees.core.config.constraints;


import com.prodnees.core.config.constraints.check.CheckValidCountryName;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CheckValidCountryName.class)
public @interface ValidCountryName {

    String message() default "not a valid country name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
