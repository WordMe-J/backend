package kr.wordme.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.wordme.model.dto.JwtDTO;
import kr.wordme.model.entity.Member;
import kr.wordme.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.Principal;

@RequiredArgsConstructor
@Component
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;

    private RequestCache requestCache = new HttpSessionRequestCache();
//    로그인 화면 이전 화면의 url 정보
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();

        Cookie[] tokenCookies = setTokenCookie(customUser);
        for (Cookie tokenCookie : tokenCookies) {
            tokenCookie.setHttpOnly(true);
            tokenCookie.setPath("/");
            response.addCookie(tokenCookie);
        }
//        쿠키에 access, refresh 토큰 정보 담고 index redirect
        response.sendRedirect("/");
    }

    private Cookie[] setTokenCookie(CustomUserDetails customUser) {
        JwtDTO jwtDTO = jwtUtil.createToken(customUser.getUsername(), String.valueOf(customUser.getAuthorities().stream().findFirst()));
        Cookie accessToken = new Cookie("access_token", jwtDTO.getAccessToken());
        Cookie refreshToken = new Cookie("refresh_token", jwtDTO.getRefreshToken());
        return new Cookie[]{accessToken,refreshToken};
    }

}
