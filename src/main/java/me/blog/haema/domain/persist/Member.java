package me.blog.haema.domain.persist;

import lombok.*;
import me.blog.haema.global.common.BaseTimeEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter //@Setter // id 값 바뀔 수 있음 (500에러 발생 가능성)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate // 변경한 필드만 update
public class Member extends BaseTimeEntity {

    // NoArgsConstructor: 기본 생성자 주입
    // AccessLevel: 생성자의 제어자 PROTECTED
    // 무분별한 객체 생성 불가능

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // id auto increment 전략
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long id;
    private String email;
    private String password;
    private String nickname;

    @Builder
    public Member(Long id, String email, String password, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    // member Email/Nickname 수정
    public void changeEmailAndNickname(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
