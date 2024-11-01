package kr.wordme.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import kr.wordme.exception.ErrorCode;
import kr.wordme.exception.member.DuplicateEmailException;
import kr.wordme.exception.member.MemberNonExistentException;
import kr.wordme.model.dto.JwtDTO;
import kr.wordme.model.dto.request.SignupRequestDTO;
import kr.wordme.model.entity.Member;
import kr.wordme.repository.MemberRepository;
import kr.wordme.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new MemberNonExistentException(
                        ErrorCode.NOT_EXIST_USER.getStatus(),
                        ErrorCode.NOT_EXIST_USER.getMessage()
                )
        );
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findByEmail(email);
    }

    public void duplicatedEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(
                    ErrorCode.DUPLICATE_EMAIL.getStatus(),
                    ErrorCode.DUPLICATE_EMAIL.getMessage()
            );
        }
    }

    public Member signUp(SignupRequestDTO signupRequestDTO) {
        if (!signupRequestDTO.isVerify()) {
            return null;
        }
        this.duplicatedEmail(signupRequestDTO.getEmail());
        String encodedPassword = passwordEncoder.encode(signupRequestDTO.getPassword());
        return memberRepository.save(Member.newInstance(signupRequestDTO, encodedPassword));
    }


    public Cookie[] signIn(SignupRequestDTO dto) {
        Member member = findByEmail(dto.getEmail());

        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("wrong password");
//            비번 틀렸을 때 spring security err 로 넘김
        }
        JwtDTO jwtDTO = jwtUtil.createToken(member.getUsername(), "ROLE_USER");
        Cookie accessToken = new Cookie("access_token", jwtDTO.getAccessToken());
        Cookie refreshToken = new Cookie("refresh_token", jwtDTO.getRefreshToken());
        return new Cookie[]{accessToken, refreshToken};
    }

    public boolean verificationEmail(String emailToken) {
        Claims claims = jwtUtil.getClaims(emailToken);
        return claims != null;
    }
}
