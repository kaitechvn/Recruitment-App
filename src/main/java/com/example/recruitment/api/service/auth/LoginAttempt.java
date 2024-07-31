package com.example.recruitment.api.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

@Component
public class LoginAttempt {
  private static final Integer MAX_ATTEMPTS = 5;
  private final RedisCacheManager redisCacheManager;

  @Autowired
  public LoginAttempt(RedisCacheManager redisCacheManager) {
    this.redisCacheManager = redisCacheManager;
  }

  @Cacheable(value = "loginAttempts", key = "#username")
  public Integer getLoginAttempts(String username) {
    return 0;
  }

  @CachePut(value = "loginAttempts", key = "#username")
  public Integer updateLoginAttempts(String username, Integer attempts) {
    return attempts + 1;
  }

  @CacheEvict(value = "loginAttempts", key = "#username")
  public void resetLoginAttempts(String username) {
  }

  // Checks if the account is locked from the cache, defaulting to false if not found
  @Cacheable(value = "accountLock", key = "#username")
  public Boolean isAccountLocked(String username) {
    return false; // Default value when cache is empty
  }

  // Unlocks the account for the given username
  @CacheEvict(value = "accountLock", key = "#username")
  public void unlockAccount(String username) {
    // Cache entry is removed
  }

  @CachePut(value = "accountLock", key = "#username")
  public Boolean checkAndHandleLock(String username, Integer attempts) {
    return attempts >= MAX_ATTEMPTS;
  }
}
