package com.example.recruitment.api.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SensitiveWordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveWord {

  String message() default "Contains sensitive word";

  String lang() default "en";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
