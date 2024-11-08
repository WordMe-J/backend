package kr.wordme.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import kr.wordme.exception.member.DuplicateException;
import kr.wordme.exception.member.InvalidParamException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<Object> handleMissingRequestParameterException(
            MissingServletRequestParameterException ex) {
        return ApiResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }


    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiResponse<Object> handleDuplicateException(DuplicateException ex) {
        return ApiResponse.of(HttpStatus.CONFLICT, ex.getMessage(), null);
    }

    @ExceptionHandler(InvalidParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<Object> handle(InvalidParamException ex) {
        return ApiResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }
}
