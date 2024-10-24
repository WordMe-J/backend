package kr.wordme.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDTO {
    private String email;
    private String password;
    private String nickname;
    private boolean isVerify;

    public static SignupRequestDTO create(String mail, String password, String nickname) {
        return null;
    }
}