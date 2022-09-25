package me.blog.haema.domain.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseTokenDto  implements Serializable {

    private final long serialVersionUID = 3L;

    private String accessToken;
    private boolean result;

    public ResponseTokenDto(String accessToken, boolean result) {
        this.accessToken = accessToken;
        this.result = result;
    }

    public static ResponseTokenDto from(final String accessToken, final boolean result) {
        return new ResponseTokenDto(accessToken, result);
    }
}
