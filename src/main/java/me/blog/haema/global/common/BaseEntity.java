package me.blog.haema.global.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass // 어노테이션이 상속을 하겠다는 의미가 아니기 때문에 @Inheritance
public class BaseEntity extends BaseTimeEntity {

    @CreatedBy // 생성자 (유저 id)
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy // 수정자 (유저 id)
    private String lastModifiedBy;
}