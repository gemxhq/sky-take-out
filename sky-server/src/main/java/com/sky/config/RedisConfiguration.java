package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    @ConditionalOnSingleCandidate
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("开始创建redis某班类...");
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        //设置redis key的序列化器，默认是JdkSerializationRedisSerializer，为便于查看，设置StringRedisSerializer序列化器。
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }
}
