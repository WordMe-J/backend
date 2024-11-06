package kr.wordme.exception.member;

import org.springframework.http.HttpStatus;

public class DuplicateException extends MemberException {

    public DuplicateException(HttpStatus status, String message) {
        super(status, message);
    }
}
