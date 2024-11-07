package kr.wordme.exception.member;

import org.springframework.http.HttpStatus;
import kr.wordme.common.CustomErrorMessage;

public class InvalidParamException extends MemberException {
    public InvalidParamException(HttpStatus status, String paramName) {
        super(status, CustomErrorMessage.INVALID_PARAMETER.format(paramName));
    }
}
