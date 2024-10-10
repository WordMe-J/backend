package kr.wordme.service;

import kr.wordme.model.dto.JwtDTO;
import kr.wordme.model.dto.request.signupRequestDTO;
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

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return getMemberByEmail(email);
    }

    public Member signUp(signupRequestDTO signupRequestDTO) throws IllegalAccessException {
        if (memberRepository.existsByEmail(signupRequestDTO.getEmail())) {
            throw new IllegalAccessException("사용 중인 이메일 입니다.");
        }
        return memberRepository.save(Member.builder()
                .id(UUID.randomUUID())
                .password(passwordEncoder.encode(signupRequestDTO.getPassword()))
                .nickname(signupRequestDTO.getNickname())
                .is_deleted(false)
                .email(signupRequestDTO.getEmail())
                .build());
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("username Not Found"));
    }

    public JwtDTO signIn(signupRequestDTO dto) {
        Member member = getMemberByEmail(dto.getEmail());

        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("wrong password");
        }

        return jwtUtil.createToken(member.getUsername(), "ROLE_USER");
    }

}
