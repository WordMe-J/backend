package kr.wordme.exception.token;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class TokenException extends RuntimeException{
    private final HttpStatus status;

    public TokenException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
