package me.blog.haema.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequestDto {

    @JsonProperty("email")
    @Email
    private String email;

    @JsonProperty("password")
    private String password;

    public static LoginRequestDto of(final String email, final String password) {
        return new LoginRequestDto(email, password);
    }
}
