package kr.wordme.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class NicknameExistsRequestDTO {
    @NotBlank(message = "Nickname cannot be empty")
    private String nickname;
}
