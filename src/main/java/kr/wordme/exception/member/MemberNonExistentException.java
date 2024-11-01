package kr.wordme.exception.member;

import org.springframework.http.HttpStatus;

public class MemberNonExistentException extends MemberException{
    public MemberNonExistentException(HttpStatus status, String message) {
        super(status, message);
    }
}
