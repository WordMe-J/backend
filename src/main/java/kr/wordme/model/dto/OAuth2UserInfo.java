package kr.wordme.model.dto;

import kr.wordme.model.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;
import java.util.UUID;

@Builder
@Getter
@ToString
public class OAuth2UserInfo {
    private String email;
    private String password;
    private String nickname;
    private String provider;

    public static OAuth2UserInfo of(String provider, Map<String, Object> attributes) throws IllegalAccessException {
        return switch (provider) {
            case "google" -> ofGoogle(attributes);
            case "naver" -> ofNaver(attributes);
            default -> throw new IllegalAccessException("지원 안하는 oauth2임");
        };
    }

    private static OAuth2UserInfo ofNaver(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .provider("naver")
                .password((String) ((Map) attributes.get("response")).get("id"))
                .nickname((String) ((Map) attributes.get("response")).get("name"))
                .email((String) ((Map) attributes.get("response")).get("email"))
                .build();
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .provider("google")
                .email((String) attributes.get("email"))
                .password((String) attributes.get("sub"))
                .nickname((String) attributes.get("name"))
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .id(UUID.randomUUID())
                .password(password)
                .nickname(nickname)
                .email(email)
                .is_deleted(false)
                .build();
    }
}
