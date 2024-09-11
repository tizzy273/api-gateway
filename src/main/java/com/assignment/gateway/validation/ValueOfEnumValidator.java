package com.assignment.gateway.validation;

import com.assignment.gateway.exception.BadRequestException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, String> {

    private List<String> acceptedValues;


    /**
     * Initialize.
     *
     * @param annotation the annotation
     */
    @Override
    public void initialize(ValueOfEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    /**
     * Checks if is valid.
     *
     * @param value   the value
     * @param context the context
     * @return true, if is valid
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if ( value != null && !acceptedValues.contains(value)) {
            throw new BadRequestException("Transaction type accepts only:" + acceptedValues.toString());
        }
        return true;
    }
}