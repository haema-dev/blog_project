package me.blog.haema.domain.persist;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.blog.haema.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    // NoArgsConstructor: 기본 생성자 주입
    // AccessLevel: 생성자의 제어자 PROTECTED
    // 무분별한 객체 생성 불가능

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // id auto increment 전략
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long id;

    public Member(Long id) {
        this.id = id;
    }
}
