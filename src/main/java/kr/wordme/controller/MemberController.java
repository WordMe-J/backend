package kr.wordme.controller;

import kr.wordme.model.dto.request.signupRequestDTO;
import kr.wordme.model.entity.Member;
import kr.wordme.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody signupRequestDTO signupRequestDTO) throws IllegalAccessException {
        Member member = memberService.signUp(signupRequestDTO);
        return ResponseEntity.ok().body(member);
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> SignIn(@RequestBody signupRequestDTO signupRequestDTO) {
        return ResponseEntity.ok().body(memberService.signIn(signupRequestDTO));
    }

    @GetMapping("/info")
    public ResponseEntity<Object> info(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok().body(member);
    }
}
