package com.mariu.catalog.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

  private List<String> enumValues;

  @Override
  public void initialize(ValidEnum constraintAnnotation) {
    enumValues = Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
        .map(Enum::name)
        .collect(Collectors.toList());

  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    boolean isValidEnum = enumValues.contains(value.toUpperCase());

    if (!isValidEnum) {
      
      context.disableDefaultConstraintViolation();
      
      context.buildConstraintViolationWithTemplate("Must be one of the following: " + enumValues)
          .addConstraintViolation();
    }

    return isValidEnum;
  }
}
