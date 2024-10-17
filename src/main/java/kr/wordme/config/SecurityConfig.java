package kr.wordme.config;

import kr.wordme.filter.JwtFilter;
import kr.wordme.repository.MemberRepository;
import kr.wordme.service.OAuth2Service;
import kr.wordme.util.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberRepository memberRepository;
    private final OAuth2Service oauth2UserService;
    private final JwtFilter jwtFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/error", "/favicon.ico");
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req ->
                        req.requestMatchers(
                                new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/members/*")
                        ).permitAll().anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)


                .headers(
                        headerConfig -> headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .oauth2Login(oauth2 -> oauth2.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oauth2UserService))
                        .successHandler(oAuth2SuccessHandler))
;
        return http.build();
    }
}
