package me.blog.haema.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import me.blog.haema.domain.member.repository.persist.Member;

import java.io.Serializable;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
public class MemberResponseDto implements Serializable {

    private final long serialVersionUID = 2L;

    @JsonProperty
    private String nickname;
    @JsonProperty
    private String email;

    public static MemberResponseDto of(Member member){
        return MemberResponseDto.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .build();
    }
}
