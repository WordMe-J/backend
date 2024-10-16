package kr.wordme.exception.member;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MemberException extends RuntimeException{

    private final HttpStatus status;

    public MemberException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
