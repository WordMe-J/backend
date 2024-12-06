package kr.wordme.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.wordme.exception.member.InvalidParamException;
import kr.wordme.exception.member.MemberException;
import kr.wordme.model.dto.JwtDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * 쿠키에 토큰이 존재하는지 검증하는 필터
 */
@RequiredArgsConstructor
public class JwtPresenceFilter extends OncePerRequestFilter {
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JwtDTO tokens = resolveTokenFromRequest(request);
        if(ObjectUtils.isEmpty(tokens)) {
//            response.sendRedirect("/");
//            throw error
            throw new InvalidParamException(HttpStatus.UNAUTHORIZED, "no tokens");
        }
        filterChain.doFilter(request, response);
    }
    private JwtDTO resolveTokenFromRequest(HttpServletRequest request) {
        Map<String, String> tokens = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (!ObjectUtils.isEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(ACCESS_TOKEN)) {
                    tokens.put(ACCESS_TOKEN, cookie.getValue());
                }
                if (cookie.getName().equals(REFRESH_TOKEN)) {
                    tokens.put(REFRESH_TOKEN, cookie.getValue());
                }
            }
            JwtDTO jwtDTO = JwtDTO.create(tokens);
            if (!ObjectUtils.isEmpty(tokens)) {
                return jwtDTO;
            }
        }
        return null;
    }
}