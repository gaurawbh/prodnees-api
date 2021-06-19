package com.prodnees.config.constraints;

import com.prodnees.config.constraints.check.ValidateSchemaName;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidateSchemaName.class)
public @interface SchemaName {

    String message() default "can only contain alphabets, numeric, and '_' characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
