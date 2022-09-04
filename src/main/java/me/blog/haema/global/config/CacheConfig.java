package me.blog.haema.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration; // 나노 초 단위의 시간 모델링

@Configuration // config 임을 알려주는 어노테이션
@RequiredArgsConstructor
public class CacheConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    /**
     * Serializer : 데이터를 전송/저장하기 쉽게 convert 해주는 과정(작업)
     * ref : https://hazelcast.com/glossary/serialization/ (상업 사이트이지만 그림 참고하기에 좋다)
     * */

    @Bean
    public CacheManager cacheManager() {
        // Redis Server 에 저장해줄 Cache 를 설정
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig() // default 지정
                // serializeKeysWith, serializeValuesWith : cache key 쌍을 직렬화, 역직렬화 하는 메소드
                .serializeKeysWith(RedisSerializationContext
                        // StringRedisSerializer : string 에서 byte 로 직렬화
                        .SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.
                        // JdkSerializationRedisSerializer : 클래스 객체로 직렬화
                        SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()))
                // 캐시 기간 설정
                .entryTtl(Duration.ofSeconds(30));

        // RedisCacheConfiguration 에서 지정할 수 없는 캐시 구성은 RedisCacheManager 에서 설정
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration).build();
    }

}
