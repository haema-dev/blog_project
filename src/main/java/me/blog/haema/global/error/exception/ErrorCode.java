package me.blog.haema.global.error.exception;

import lombok.Getter;

@Getter // 변경 불가능 해야하므로 getter 만 사용
public enum ErrorCode { // enum 으로 지정된 status 만 사용

    // Common
    METHOD_NOT_ALLOWED(405, "C001", "잘못된 요청입니다."),
    HANDLE_ACCESS_DENIED(400, "C002", "잘못된 접근입니다."),
    INVALID_INPUT_VALUE(400, "C003", "잘못된 입력입니다."),
    ENTITY_NOT_FOUND(400, "C004", "개체를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "C005", "서버 내부 오류 입니다."),
    INVALID_TYPE_VALUE(400, "C006", "잘못된 타입 입니다."),

    // Member
    PASSWORD_NULL_ERROR(400, "M001", "비밀번호가 없습니다."),
    USER_NOT_FOUND(400, "M002", "없는 회원 입니다."),
    DUPLICATE_EMAIL(400, "M004", "이메일이 이미 존재합니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}