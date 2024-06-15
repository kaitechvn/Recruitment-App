package com.example.recruitment.api.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

public class SensitiveWordValidator implements ConstraintValidator<SensitiveWord, String> {

  private String lang;
  private static final String[] SENSITIVE_ENG_WORDS = {"shit", "wtf"};
  private static final String[] SENSITIVE_VN_WORDS = {"vl", "dm"};
  private static final Map<String, String[]> sensitiveWordsMap = new HashMap<>();

  static {
    sensitiveWordsMap.put("en", SENSITIVE_ENG_WORDS);
    sensitiveWordsMap.put("vn", SENSITIVE_VN_WORDS);
  }

  @Override
  public void initialize(SensitiveWord constraintAnnotation) {
    this.lang = constraintAnnotation.lang();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isEmpty()) {
      return true; // Consider null or empty values as valid. Adjust if necessary.
    }

    String[] sensitiveWords = sensitiveWordsMap.get(lang);
    if (sensitiveWords == null) {
      return true; // If no sensitive words are defined for the language, consider it valid.
    }

    return !containsSensitiveWord(value, sensitiveWords);
  }

  private boolean containsSensitiveWord(String value, String[] sensitiveWords) {
    for (String word : sensitiveWords) {
      if (value.contains(word)) {
        return true;
      }
    }
    return false;
  }
}
