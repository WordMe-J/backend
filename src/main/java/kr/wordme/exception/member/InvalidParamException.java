package kr.wordme.exception.member;

import org.springframework.http.HttpStatus;

public class InvalidParamException extends MemberException {
    public InvalidParamException(HttpStatus status, String paramName) {
        super(status, paramName);
    }
}
