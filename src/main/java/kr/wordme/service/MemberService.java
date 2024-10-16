package kr.wordme.service;

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

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                ()->new MemberNonExistentException(
                        ErrorCode.NOT_EXIST_USER.getStatus(),
                        ErrorCode.NOT_EXIST_USER.getMessage()
                )
        );
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findByEmail(email);
    }

    public Member signUp(SignupRequestDTO signupRequestDTO) throws IllegalAccessException {
        if (memberRepository.existsByEmail(signupRequestDTO.getEmail())) {
            throw new DuplicateEmailException(
                    ErrorCode.DUPLICATE_EMAIL.getStatus(),
                    ErrorCode.DUPLICATE_EMAIL.getMessage()
            );
        }
        String encodedPassword = passwordEncoder.encode(signupRequestDTO.getPassword());
        return memberRepository.save(Member.newInstance(signupRequestDTO, encodedPassword));
    }

    public JwtDTO signIn(SignupRequestDTO dto) {
        Member member = findByEmail(dto.getEmail());

        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("wrong password");
//            비번 틀렸을 때 spring security err 로 넘김
        }
        return jwtUtil.createToken(member.getUsername(), "ROLE_USER");
    }
}
