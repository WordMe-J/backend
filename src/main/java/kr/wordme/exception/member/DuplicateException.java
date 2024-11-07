package kr.wordme.exception.member;

import org.springframework.http.HttpStatus;
import kr.wordme.common.CustomErrorMessage;

public class DuplicateException extends MemberException {

    public DuplicateException(HttpStatus status, String param) {
        super(status, CustomErrorMessage.DUPLICATE_VALUE.format(param));
    }
}
