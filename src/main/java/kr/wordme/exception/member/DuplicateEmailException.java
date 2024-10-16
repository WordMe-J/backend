package kr.wordme.exception.member;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends MemberException{

    public DuplicateEmailException(HttpStatus status, String message) {
        super(status, message);
    }
}
