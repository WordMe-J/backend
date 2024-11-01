package kr.wordme.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.wordme.model.dto.JwtDTO;
import kr.wordme.util.JwtAuthUtil;
import kr.wordme.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtAuthUtil jwtAuthUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JwtDTO tokens = resolveTokenFromRequest(request);
        if (ObjectUtils.isEmpty(tokens)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (jwtAuthUtil.validateToken(tokens.getAccessToken())) {
            Authentication auth = jwtAuthUtil.getAUthentication(tokens.getAccessToken());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            String newAccessToken = jwtAuthUtil.validateToken(tokens);
            if (!StringUtils.hasText(newAccessToken)) {
//                new Access Token 이 null 일 때 -> refresh Token 이 만료되었을 때
//                access_token, refresh_token 삭제
                Cookie[] deleteCookies = cookieDelete();
                for (Cookie deleteCookie : deleteCookies) {
                    response.addCookie(deleteCookie);
                }
//                refresh token 만료 되었을 때 달린 쿠키들을 삭제하고 index page 로 리다이렉트
                response.sendRedirect("/");
            }

            Cookie newCookie = new Cookie("access_token", newAccessToken);
            newCookie.setHttpOnly(true);
            newCookie.setPath("/");
            response.addCookie(newCookie);

            Authentication auth = jwtAuthUtil.getAUthentication(newAccessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    public Cookie[] cookieDelete() {
        Cookie accessDeleteCookie = new Cookie("access_token", null);
        accessDeleteCookie.setPath("/");
        accessDeleteCookie.setMaxAge(0);

        Cookie refreshDeleteCookie = new Cookie("refresh_token", null);
        refreshDeleteCookie.setMaxAge(0);
        refreshDeleteCookie.setPath("/");

        return new Cookie[]{accessDeleteCookie, refreshDeleteCookie};
    }

    private JwtDTO resolveTokenFromRequest(HttpServletRequest request) {
        Map<String, String> tokens = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (!ObjectUtils.isEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("access_token")) {
                    tokens.put("accessToken", cookie.getValue());
                }
                if (cookie.getName().equals("refresh_token")) {
                    tokens.put("refreshToken", cookie.getValue());
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
