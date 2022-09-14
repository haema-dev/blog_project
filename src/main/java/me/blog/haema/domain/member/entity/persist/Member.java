package me.blog.haema.domain.member.entity.persist;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.blog.haema.domain.member.entity.vo.RoleType;
import me.blog.haema.global.common.BaseTimeEntity;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter //@Setter // id 값 바뀔 수 있음 (500에러 발생 가능성)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate // 변경한 필드만 update
@Where(clause = "activated = true")
public class Member extends BaseTimeEntity {

    // NoArgsConstructor: 기본 생성자 주입
    // AccessLevel: 생성자의 제어자 PROTECTED
    // 무분별한 객체 생성 불가능

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String email;

    @Column(nullable = false)
    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    private boolean activated = true;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @Builder
    public Member(Long id, String email, String password, String nickname, RoleType role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    // member 수정
    public void change(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
    }

    // member 삭제
    public void delete() {
        this.activated = false;
        recordDeleteTime();
    }

    //toString()은 테스트 할 땐 괜찮지만 리소스 낭비이므로 commit 은 하지 말 것.
}
