package kr.wordme.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtAuthUtil jwtAuthUtil;
    public static final String JWT_HEADER_KEY = "Authorization";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveTokenFromRequest(request);

        if(!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        if(jwtAuthUtil.validateToken(token)) {
            Authentication auth = jwtAuthUtil.getAUthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String resolveTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(JWT_HEADER_KEY);

        if(!ObjectUtils.isEmpty(token)) {
            return token;
        }
        return null;
    }
}
