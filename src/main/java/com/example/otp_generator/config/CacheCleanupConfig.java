package com.example.otp_generator.config;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;

public class CacheCleanupConfig {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @PreDestroy
    public void clearRedisCache() {
        try {
            // Deletes all keys from the Redis database
            redisTemplate.getConnectionFactory().getConnection().flushDb();
            System.out.println("Redis cache cleared successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error clearing Redis cache: " + e.getMessage());
        }
    }
}
