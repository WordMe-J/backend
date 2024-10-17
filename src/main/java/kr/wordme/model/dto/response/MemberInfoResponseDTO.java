package kr.wordme.model.dto.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MemberInfoResponseDTO {
    private String nickname;
    public static MemberInfoResponseDTO of(String nickname) {
        return new MemberInfoResponseDTO(nickname);
    }
}
