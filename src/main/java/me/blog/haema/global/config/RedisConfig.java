package me.blog.haema.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration // config 임을 알려주는 어노테이션
public class RedisConfig { // "키-값" 구조의 비정형 데이터를 저장하고 관리 (Remote Dictionary Server 의 약자)

    private String host = "127.0.0.1";

    private int port = 6379;

    // Thread-safe connections
    // Thread-safe : 멀티 쓰레드 환경에서 동시접근으로 인해 데드락 등의 일이 발생하는 것에 대해 안전
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Redis 연결을 위한 설치
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        // 연결을 위한 인스턴스 생성
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    // 데이터 액세스 코드를 단순화 (자동 직렬화/역직렬화)
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
