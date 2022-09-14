package me.blog.haema.domain.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.blog.haema.domain.member.entity.persist.Member;
import me.blog.haema.domain.member.entity.vo.RoleType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class JoinRequestDto {

    private String nickname;
    @Email
    private String email;
    private String password;
    private RoleType role;

    // password 암호화
    public static String encode(final String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    // builder 를 이용하여 dto 를 Entity 로 변환할 toEntity() 메소드 추가
    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(encode(password))
                .nickname(nickname)
                .role(role)
                .build()
                ;
    }
}
