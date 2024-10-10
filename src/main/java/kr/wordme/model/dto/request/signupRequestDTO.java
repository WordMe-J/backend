package kr.wordme.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class signupRequestDTO {
    private UUID id;
    private String email;
    private String password;
    private String nickname;
}
