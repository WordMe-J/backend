package kr.wordme.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.jmx.export.annotation.ManagedNotifications;

import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtDTO {
    private String accessToken;
    private String refreshToken;

    public static JwtDTO create(Map<String, String> tokens) {
        return new JwtDTO(tokens.get("accessToken"),tokens.get("refreshToken"));
    }
}
