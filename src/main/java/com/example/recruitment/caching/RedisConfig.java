package com.example.recruitment.caching;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfig {

  private RedisCacheConfiguration createCacheConfiguration(Duration ttl) {
    return RedisCacheConfiguration.defaultCacheConfig()
      .entryTtl(ttl)
      .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
      .disableCachingNullValues();
  }


  @Bean
  public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration defaultCacheConfig = createCacheConfiguration(Duration.ofMinutes(3));

    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
    cacheConfigurations.put("loginAttempts", createCacheConfiguration(Duration.ofSeconds(60)));
    cacheConfigurations.put("accountLock", createCacheConfiguration(Duration.ofMinutes(2)));

    return RedisCacheManager.builder(redisConnectionFactory)
      .cacheDefaults(defaultCacheConfig)
      .withInitialCacheConfigurations(cacheConfigurations)
      .build();
  }
  }


