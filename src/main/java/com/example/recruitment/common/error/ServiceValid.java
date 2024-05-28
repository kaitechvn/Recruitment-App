package com.example.recruitment.common.error;

import com.example.recruitment.entity.Field;
import com.example.recruitment.entity.Province;
import com.example.recruitment.repository.FieldRepository;
import com.example.recruitment.repository.ProvinceRepository;

import java.util.*;
import java.util.function.Function;

public class ServiceValid {

  public static String validateIds(
    List<Integer> provinceIds,
    List<Integer> fieldIds,
    Integer entityId,
    ProvinceRepository provinceRepository,
    FieldRepository fieldRepository,
    Function<Integer, Optional<?>> entityCheckFunction,
    String entityType
  ) {
    Map<Integer, String> idTypeMap = new HashMap<>();
    Map<Integer, Optional<?>> idCheckMap = new HashMap<>();

    if (provinceIds != null) {
      for (Integer pid : provinceIds) {
        Optional<Province> province = provinceRepository.findById(pid);
        if (province.isEmpty()) {
          idTypeMap.put(pid, "Province");
          idCheckMap.put(pid, province);
        }
      }
    }

    if (fieldIds != null) {
      for (Integer fid : fieldIds) {
        Optional<Field> field = fieldRepository.findById(fid);
        if (field.isEmpty()) {
          idTypeMap.put(fid, "Field");
          idCheckMap.put(fid, field);
        }
      }
    }

    if (entityId != null) {
      Optional<?> entity = entityCheckFunction.apply(entityId);
      if (entity.isEmpty()) {
        idTypeMap.put(entityId, entityType);
        idCheckMap.put(entityId, entity);
      }
    }

    return ServiceValid.collectIdNotExistMessage(idTypeMap, idCheckMap);
  }

  private static String collectIdNotExistMessage(Map<Integer, String> idTypeMap, Map<Integer, Optional<?>> idCheckMap) {
    List<String> errorMessages = new ArrayList<>();

    for (Map.Entry<Integer, String> entry : idTypeMap.entrySet()) {
      Integer id = entry.getKey();
      String entityType = entry.getValue();
      Optional<?> checkResult = idCheckMap.get(id);

      if (checkResult.isEmpty()) {
        errorMessages.add(entityType + " ID " + id);
      }
    }

    if (!errorMessages.isEmpty()) {
      StringBuilder errorMessageBuilder = new StringBuilder("Errors: ");
      for (String errorMessage : errorMessages) {
        errorMessageBuilder.append(errorMessage).append(", ");
      }
      errorMessageBuilder.setLength(errorMessageBuilder.length() - 2); // Remove the last comma and space
      errorMessageBuilder.append(" doesn't exist.");
      return errorMessageBuilder.toString();
    }

    return "";
  }
}
