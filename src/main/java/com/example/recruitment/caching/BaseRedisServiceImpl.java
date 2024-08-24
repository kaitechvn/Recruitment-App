package com.example.recruitment.caching;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BaseRedisServiceImpl implements BaseRedisService {


  @Override
  public void set(String key, String value) {

  }

  @Override
  public void setTimeToLive(String key, long timeToLive) {

  }

  @Override
  public void setHash(String key, String field, Object value) {

  }

  @Override
  public boolean isHashExists(String key, String field) {
    return false;
  }

  @Override
  public Object get(String key) {
    return null;
  }

  @Override
  public Object getHash(String key, String field) {
    return null;
  }

  @Override
  public Map<String, Object> getFields(String key) {
    return Map.of();
  }

  @Override
  public void delete(String key) {

  }

  @Override
  public void delete(String key, String field) {

  }

  @Override
  public void delete(String key, List<Object> fields) {

  }
}
