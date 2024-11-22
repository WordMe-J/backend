package kr.wordme.controller;

import kr.wordme.model.dto.response.VerificationEmailResponseDTO;
import kr.wordme.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final MemberService memberService;

    @GetMapping("members/verify")
    public String verificationEmail(@RequestParam("token") String token, @RequestParam("email") String email) {
        boolean verify = memberService.verificationEmail(token);
//        VerificationEmailResponseDTO responseDTO = VerificationEmailResponseDTO.create(email, verify);
        if (verify) {
            return "redirect:/";
        }
            return "redirect:/error.html";

        /**
         *  1. redirect 방식으로 이메일 검증
         *  2. 이메일 재전송
         */
    }
}
