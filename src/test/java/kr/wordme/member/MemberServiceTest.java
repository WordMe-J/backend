package kr.wordme.member;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import kr.wordme.model.dto.request.SignupRequestDTO;
import kr.wordme.model.entity.Member;
import kr.wordme.repository.MemberRepository;
import kr.wordme.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("검증되지 않은 이메일로 회원가입")
    public void testSignUp_Failure_VerificationNotPassed() {
//        given
        SignupRequestDTO signupRequestDTO = SignupRequestDTO.create("test@example.com","1234","test",false);
//        when
        Member result = memberService.signUp(signupRequestDTO);
//        then
        assertNull(result);
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    @DisplayName("중복된 이메일로 회원가입")
    public void testSignUp_DuplicateEmail() {

    }
}


