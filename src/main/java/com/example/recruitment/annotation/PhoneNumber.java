package com.example.recruitment.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PhoneNumberValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface PhoneNumber {

  String message() default "{phone.number.invalid}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
