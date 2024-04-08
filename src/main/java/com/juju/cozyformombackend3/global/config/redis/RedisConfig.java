package com.juju.cozyformombackend3.global.config.redis;

//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.serializer.StringRedisSerializer;
//
// import lombok.RequiredArgsConstructor;
//
// @Configuration
// @RequiredArgsConstructor
public class RedisConfig {
    //     private final RedisProperties redisProperties;
    //
    //     @Bean
    //     public RedisConnectionFactory redisConnectionFactory() {
    //         return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    //     }
    //
    //     @Bean
    //     public RedisTemplate<String, Object> redisTemplate() {
    //         RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    //         redisTemplate.setConnectionFactory(redisConnectionFactory());
    //         redisTemplate.setKeySerializer(new StringRedisSerializer());
    //         redisTemplate.setValueSerializer(new StringRedisSerializer());
    //
    //         //
    //         // // 일반적인 key:value의 경우 시리얼라이저
    //         //
    //         // // Hash를 사용할 경우 시리얼라이저
    //         // redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    //         // redisTemplate.setHashValueSerializer(new StringRedisSerializer());
    //
    //         // 모든 경우
    //         // redisTemplate.setDefaultSerializer(new StringRedisSerializer());
    //
    //         return redisTemplate;
    //     }
}
