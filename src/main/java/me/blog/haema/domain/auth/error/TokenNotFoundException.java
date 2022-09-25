package me.blog.haema.domain.auth.error;

import lombok.Getter;
import me.blog.haema.global.error.exception.ErrorCode;

@Getter
public class TokenNotFoundException extends RuntimeException {

    private ErrorCode errorCode;

    public TokenNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.getMessage();
    }
}
