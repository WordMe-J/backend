package kr.wordme.model.dto.response;

import kr.wordme.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberInfoResponseDTO {
    private String nickname;
    public static MemberInfoResponseDTO from(Member member) {
        return new MemberInfoResponseDTO(member.getNickname());
    }
}
