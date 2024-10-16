package kr.wordme.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class SignupRequestDTO {
    private UUID id;
    private String email;
    private String password;
    private String nickname;
}
