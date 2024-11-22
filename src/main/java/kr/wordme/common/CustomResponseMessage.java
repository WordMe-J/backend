package kr.wordme.common;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomResponseMessage {
    private final Object message;

    public static CustomResponseMessage of(String message) {
        return new CustomResponseMessage(message);
    }
}
