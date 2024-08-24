package com.example.recruitment.caching;

import java.util.List;
import java.util.Map;

public interface BaseRedisService {

  void set(String key, String value);

  void setTimeToLive(String key, long timeToLive);

  void setHash(String key, String field, Object value);

  boolean isHashExists(String key, String field);

  Object get(String key);

  Object getHash(String key, String field);

  public Map<String, Object> getFields(String key);

  void delete(String key);

  void delete(String key, String field);

  void delete(String key, List<Object> fields);


}
