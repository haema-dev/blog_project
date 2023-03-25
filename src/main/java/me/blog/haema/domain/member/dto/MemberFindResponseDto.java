package me.blog.haema.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.blog.haema.domain.member.entity.persist.Member;


@Getter
@NoArgsConstructor
public class MemberFindResponseDto {
    private String email;
    private String nickname;

    @Builder
    public MemberFindResponseDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public static MemberFindResponseDto of(Member member){
        return MemberFindResponseDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
}