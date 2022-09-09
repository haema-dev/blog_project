package me.blog.haema.domain.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.blog.haema.domain.member.repository.persist.Member;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberUpdateRequestDto {

    private String nickname;
    @Email
    private String email;
    private String password;

    // builder 를 이용하여 dto 를 Entity 로 변환할 toEntity() 메소드 추가
    public Member toEntity() {
        return Member.builder()
                .email(getEmail())
                .password(getPassword())
                .nickname(getNickname())
                .build()
                ;
    }
}
