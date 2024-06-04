package com.example.recruitment.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SensitiveWordValidator implements ConstraintValidator<SensitiveWord, String> {

  // List of sensitive words to check against
  private static final String[] SENSITIVE_WORDS = {"shit", "wtf", "asshole"};

  @Override
  public void initialize(SensitiveWord sensitiveWord) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true; // null values are considered valid
    }

    // Convert to lower case for case-insensitive comparison
    String lowerCaseValue = value.toLowerCase();

    // Check if the value contains any sensitive words
    for (String word : SENSITIVE_WORDS) {
      if (lowerCaseValue.contains(word)) {
        return false; // Contains sensitive word
      }
    }

    return true; // No sensitive words found
  }
}
