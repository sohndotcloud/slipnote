package com.sohn.SlipNote.service;


import com.sohn.SlipNote.util.AESUtil;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;

@Service
public class RedisService {
    private RedisTemplate<String, String> template;
    private SecretKey secretKey;

    public RedisService(RedisTemplate<String, String> redisTemplate) throws Exception {
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
        connectionFactory.afterPropertiesSet();

        template = redisTemplate;
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(StringRedisSerializer.UTF_8);
        template.afterPropertiesSet();
        secretKey = AESUtil.generateKey();
    }

    public void setEncrypted(String key, String message, Duration ttl) throws Exception {
        String encrypted = AESUtil.encrypt(message, secretKey);
        template.opsForValue().set(key, encrypted, ttl);
    }

    public String getDecrypted(String key) throws Exception {
        String encrypted = template.opsForValue().get(key);
        if (encrypted == null) return null;
        return AESUtil.decrypt(encrypted, secretKey);
    }
}
