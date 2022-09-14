package me.blog.haema.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.blog.haema.global.error.exception.ErrorCode;

@Getter // 변경 불가능 해야하므로 getter 만 사용
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String message;
    private int status;
    private String code;

    private ErrorResponse(final ErrorCode errorCode) {
        message = errorCode.getMessage();
        status = errorCode.getStatus();
        code = errorCode.getCode();
    }

    private ErrorResponse(String message, int status, String code) {
        this.message = message;
        this.status = status;
        this.code = code;
    }

    public static ErrorResponse createBinding(final String message, final int status, final String code) {
        return new ErrorResponse(message, status, code);
    }

    public static ErrorResponse of(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }
}