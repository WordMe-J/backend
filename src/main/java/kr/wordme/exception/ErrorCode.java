package kr.wordme.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    NOT_EXIST_USER(HttpStatus.NOT_FOUND, "non-existent user"),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "duplicated email"),
    FAIL_SEND_EMAIL(HttpStatus.BAD_REQUEST,"fail to send email");

    private final HttpStatus status;
    private final String message;
}
