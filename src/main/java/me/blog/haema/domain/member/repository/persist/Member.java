package me.blog.haema.domain.member.repository.persist;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    // auto increment 전략은 id 를 db 에게 맡기겠다는 의미이다. 즉, id 데이터가 만들어지는 건 db 에서 데이터를 저장하는 순간.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // id auto increment 전략
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String nickname;

    @Builder
    public Member(Long id, String email, String password, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        super.getCreateTime();
    }

    // member 수정
    public void change(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
        super.getLastModifiedTime();
    }

    //toString()은 테스트 할 땐 괜찮지만 리소스 낭비이므로 commit 은 하지 말 것.
}
