package kr.wordme.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.jmx.export.annotation.ManagedNotifications;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtDTO {
    private String accessToken;
    private String refreshToken;
}
