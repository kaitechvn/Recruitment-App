package com.example.recruitment.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

  private static final String PHONE_NUMBER_PATTERN = "\\d{10}";


  @Override
  public void initialize(PhoneNumber phoneNumber) {
  }

  @Override
  public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
    if (phoneNumber == null) {
      return false;
    }
    return phoneNumber.matches(PHONE_NUMBER_PATTERN);
  }

}
