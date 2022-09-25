package me.blog.haema.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequestDto {

    @JsonProperty("email")
    @Email
    @NotBlank
    private String email;

    @JsonProperty("password")
    @NotBlank
    private String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // 정적 팩토리 메서드 (객체 생성 관리 용이)
    public static LoginRequestDto of(final String email, final String password) {
        return new LoginRequestDto(email, password);
    }
}
