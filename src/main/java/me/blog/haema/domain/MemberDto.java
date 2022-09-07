package me.blog.haema.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.blog.haema.domain.persist.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {

    /**
     * DB와 직접적으로 연관성이 있는 Entity 와 Controller 에서 받은 Dto 를 분리한다.
     * */
    private Long id;
    private String nickname;
    private String email;
    private String password;

    public MemberDto(Long id, String email, String password, String nickname) {
        // auto increment 전략은 id 를 db 에게 맡기겠다는 의미이다. 즉, id 데이터가 만들어지는 건 db 에서 데이터를 저장하는 순간.
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    // builder 를 이용하여 dto 를 Entity 로 변환할 toEntity() 메소드 추가
    public Member toEntity() {
            return Member.builder()
                    .id(getId())
                    .email(getEmail())
                    .password(getPassword())
                    .nickname(getNickname())
                    .build()
            ;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
