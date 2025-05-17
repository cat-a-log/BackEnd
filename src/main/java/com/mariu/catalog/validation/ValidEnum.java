package com.mariu.catalog.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {
  Class<? extends Enum<?>> enumClass();

  String message() default "Must be a valid value from {enumClass}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
