package com.assignent.gateway.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = TransactionValidator.class)
public @interface TransactionValid {

    /**
     * Class<?>[] groups.
     *
     * @return Class<?>[]
     */
    Class<?>[] groups() default {};

    /**
     * String message.
     *
     * @return String
     */
    String message() default "";

    /**
     * Class<? extends Payload>[] payload.
     *
     * @return Class<? extends Payload>[]
     */
    Class<? extends Payload>[] payload() default {};


}