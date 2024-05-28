package com.example.recruitment.common.data_transform;

import java.util.ArrayList;
import java.util.List;

public class Converter {
  public static String ListToStringDb(List<Integer> list) {
    if (list == null) {
      return null;
    }

    StringBuilder sb = new StringBuilder("-");
    for (Integer element : list) {
      sb.append(element).append("-");
    }
    return sb.toString();
  }

  public static List<Integer> extractIdFromStringDb(String formattedString) {
    String[] splitArray = formattedString.split("-");
    List<Integer> ids = new ArrayList<>();

    for (String id : splitArray) {
      if (!id.isEmpty()) {
        ids.add(Integer.parseInt(id));
      }
    }

    return ids;
  }
}
