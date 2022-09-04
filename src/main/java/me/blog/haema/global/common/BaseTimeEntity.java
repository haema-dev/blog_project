package me.blog.haema.global.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 하위 클래스(상속할 클래스)에 엔티티 적용
@EntityListeners(AuditingEntityListener.class) // EntityListeners: 콜백 수신을 지정하는 어노테이션 (매핑된 부모 클래스에 사용가능) // AuditingEntityListener: 영속성 수신 정보 및 update entity 체크
public class BaseTimeEntity { // 실제로 존재하지 않는 엔티티(테이블 없음)

    @CreatedDate // 필드가 생성된 date 로 선언
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    @LastModifiedDate // 최근 엔티티를 수정한 date 로 선언
    @Column(name = "last_modified_time")
    private LocalDateTime lastModifiedTime;

    @Column(name = "delete_time")
    private LocalDateTime deleteTime;

    public void recordDeleteTime() {
        this.deleteTime = LocalDateTime.now();
    }
}