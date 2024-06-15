package com.example.recruitment.api.mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Mapper {

  public static void UpdateNonNull(Object source, Object target) {
    if (source == null || target == null) {
      throw new IllegalArgumentException("Source and target must not be null");
    }

    Field[] sourceFields = source.getClass().getDeclaredFields();
    Field[] targetFields = target.getClass().getDeclaredFields();

    for (Field sourceField : sourceFields) {
      sourceField.setAccessible(true);
      try {
        Object value = sourceField.get(source);
        if (value != null) {
          Field targetField = findFieldByName(targetFields, sourceField.getName());
          if (targetField != null) {
            targetField.setAccessible(true);
            if (value instanceof List) {
              // Handle List type
              List<?> sourceList = (List<?>) value;
              List<Object> targetList = new ArrayList<>(sourceList.size());
              for (Object item : sourceList) {
                targetList.add(item); // Shallow copy if not cloneable
              }
              targetField.set(target, targetList);
            } else if (isSimpleValueType(value.getClass())) {
              // Handle simple value types
              targetField.set(target, value);
            } else {
              // Handle nested objects
              Object targetNestedObject = targetField.get(target);
              if (targetNestedObject == null) {
                targetNestedObject = targetField.getType().newInstance();
                targetField.set(target, targetNestedObject);
              }
              UpdateNonNull(value, targetNestedObject);
            }
          }
        }
      } catch (IllegalAccessException | InstantiationException e) {
        e.printStackTrace();
      }
    }
  }

  private static Field findFieldByName(Field[] fields, String name) {
    for (Field field : fields) {
      if (field.getName().equals(name)) {
        return field;
      }
    }
    return null;
  }

  private static boolean isSimpleValueType(Class<?> clazz) {
    return clazz.isPrimitive() ||
      clazz.equals(String.class) ||
      clazz.equals(Integer.class) ||
      clazz.equals(Long.class) ||
      clazz.equals(Float.class) ||
      clazz.equals(Double.class) ||
      clazz.equals(Boolean.class) ||
      clazz.equals(Byte.class) ||
      clazz.equals(Character.class) ||
      clazz.equals(Short.class) ||
      clazz.equals(Void.class) ||
      clazz.equals(Date.class);
  }

}
