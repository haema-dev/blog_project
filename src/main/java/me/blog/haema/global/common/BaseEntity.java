package me.blog.haema.global.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass // 왜 MappedSuperclass 인지 잘 이해가 안 간다ㅜㅜ @Inheritance 를 써야 맞지 않을까?
public class BaseEntity extends BaseTimeEntity {

    @CreatedBy // 생성자 (유저 id)
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy // 수정자 (유저 id)
    private String lastModifiedBy;
}