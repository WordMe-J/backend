package kr.wordme.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EmailExistsRequestDTO {
    @NotEmpty
    @NotNull
    private String email;

    public EmailExistsRequestDTO(String email) {
        this.email = email;
    }
}
