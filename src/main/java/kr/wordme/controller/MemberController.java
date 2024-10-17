package kr.wordme.controller;

import jakarta.mail.MessagingException;
import kr.wordme.common.CustomResponseMessage;
import kr.wordme.model.dto.request.SignupRequestDTO;
import kr.wordme.model.dto.response.VerificationEmailResponseDTO;
import kr.wordme.model.entity.Member;
import kr.wordme.service.EmailService;
import kr.wordme.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final EmailService mailService;

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody SignupRequestDTO signupRequestDTO) throws IllegalAccessException {
        Member member = memberService.signUp(signupRequestDTO);
        return ResponseEntity.ok().body(member);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> SignIn(@RequestBody SignupRequestDTO signupRequestDTO) {
        return ResponseEntity.ok().body(memberService.signIn(signupRequestDTO));
    }

    @GetMapping("/info")
    public ResponseEntity<Object> info(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok().body(member);
    }

    @PostMapping("/send-email")
    public ResponseEntity<Object> sendEmail(@RequestParam("email") String email) throws MessagingException {
        memberService.duplicatedEmail(email); //메일 중복
        mailService.sendEmail(email); //검증 링크 전송

//        1. 이메일 인증 버튼 클릭 시 이메일로 리액트 리다이렉트 링크 발송
//        2. 리액트에서 /verify 엔드포인트로 유효한 토큰인지 검증
//        3. result 가 true 면 인증되었다는 표시와 함께 회원가입 절차 진행

//        temp : 링크 클릭하면 boolean 담긴 dto 반환하도록 구현
        return ResponseEntity.ok().body(new CustomResponseMessage("success to send email"));
    }

    @GetMapping("/verify")
    public ResponseEntity<Object> verificationEmail(@RequestParam("token") String token, @RequestParam("email") String email) {
        boolean verify = memberService.verificationEmail(token);
        VerificationEmailResponseDTO responseDTO = VerificationEmailResponseDTO.create(email,verify);
        return ResponseEntity.ok().body(responseDTO);
//        이메일 토큰 검증 후 유효한 토큰이면 true 반환
    }
}
