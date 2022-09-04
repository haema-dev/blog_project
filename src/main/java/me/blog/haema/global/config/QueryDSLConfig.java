package me.blog.haema.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration // config 임을 알려주는 어노테이션
public class QueryDSLConfig {

    // 순수 JPA 사용
    @PersistenceContext
    private EntityManager entityManager;

    // 쿼리 생성을 위한 클래스 Bean 주입
    // JPQL 쿼리에 대한 메소드가 존재하는 interface 를 상속 받아서 JPAQuery class 에서 구현(재정의)
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(this.entityManager);
    }
}
