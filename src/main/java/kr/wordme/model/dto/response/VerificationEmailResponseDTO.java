package kr.wordme.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class VerificationEmailResponseDTO {
    private String email;
    private boolean result;

    public static VerificationEmailResponseDTO create(String email, boolean result) {
        return new VerificationEmailResponseDTO(email, result);
    }
}
