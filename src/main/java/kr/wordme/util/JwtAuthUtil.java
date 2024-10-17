package kr.wordme.util;

import io.jsonwebtoken.Claims;
import kr.wordme.exception.ErrorCode;
import kr.wordme.exception.member.MemberException;
import kr.wordme.exception.token.TokenException;
import kr.wordme.model.dto.JwtDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtAuthUtil {
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public boolean validateToken(String token) {
        if(!StringUtils.hasText(token)) {
            return false;
        }
        Claims claims = jwtUtil.getClaims(token);
        return claims != null;
    }
    //    재발급용
    public String validateToken(JwtDTO jwtDTO) {
        if(!StringUtils.hasText(jwtDTO.getAccessToken()) || !StringUtils.hasText(jwtDTO.getRefreshToken())) {
            throw new NullPointerException("no jwt parameter");
        }

        Claims accessClaims = jwtUtil.getClaims(jwtDTO.getAccessToken());
        Claims refreshClaims = jwtUtil.getClaims(jwtDTO.getRefreshToken());

        if(accessClaims != null && refreshClaims != null && jwtUtil.getSubject(accessClaims).equals(jwtUtil.getSubject(refreshClaims))) {
            return jwtUtil.createNewAccessToken(refreshClaims);
        } else {
            throw new TokenException(
                    ErrorCode.NOT_EXIST_TOKEN.getStatus(),
                    ErrorCode.NOT_EXIST_TOKEN.getMessage()
            );
        }
    }
    public Authentication getAUthentication(String token) {
        Claims claims = jwtUtil.getClaims(token);
        String email = jwtUtil.getSubject(claims);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
