package me.blog.haema.domain.member.error;

import lombok.Getter;
import me.blog.haema.global.error.exception.ErrorCode;

@Getter
public class DuplicateMemberEmailException extends RuntimeException {

    private ErrorCode errorCode;

    public DuplicateMemberEmailException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.getMessage();
    }
}
