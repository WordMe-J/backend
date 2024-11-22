package kr.wordme.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    NOT_EXIST_USER(HttpStatus.NOT_FOUND, "non-existent user"), //404

    INVALID_ACCOUNT(HttpStatus.BAD_REQUEST, "Invalid account"), //400

    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "duplicated email"), //409

    FAIL_SEND_EMAIL(HttpStatus.BAD_REQUEST,"fail to send email"), //400

    NOT_EXIST_TOKEN(HttpStatus.NOT_FOUND, "token IS null"), //404

    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "token is expired"); //400

    private final HttpStatus status;
    private final String message;
}
