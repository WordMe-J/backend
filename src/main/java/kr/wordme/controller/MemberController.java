package kr.wordme.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.wordme.common.CustomResponseMessage;
import kr.wordme.filter.JwtFilter;
import kr.wordme.model.dto.request.SignupRequestDTO;
import kr.wordme.model.dto.response.MemberInfoResponseDTO;
import kr.wordme.model.dto.response.VerificationEmailResponseDTO;
import kr.wordme.model.entity.Member;
import kr.wordme.service.EmailService;
import kr.wordme.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {
    /**
     * # 자사 회원가입 & 로그인
     * 1. 이메일 검증 (30분 유효한 임시 토큰으로 검증)
     * 2. 이메일 검증 결과(isVerify), 비밀번호 등등 담긴 signupRequestDTO 로 회원가입 진행
     * 3. 검증 결과가 true 일 때만 회원가입 완료
     * <p/>
     * 4. 이메일, 비밀번호로 로그인 진행 access, refresh token cookie 에 담고 index redirect
     * # oAuth2 회원가입 & 로그인
     * 1. DB에 같은 이메일 있는지 확인 후 DB에 저장
     * 2. 회원가입 / 로그인 성공 시 token cookie 에 담고 index redirect
     * 3. Header 에 token 담고 정보 요청
     **/

    private final MemberService memberService;
    private final EmailService mailService;
    private final JwtFilter jwtFilter;

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody SignupRequestDTO signupRequestDTO) {
        Member member = memberService.signUp(signupRequestDTO);
        boolean signUpResult = member != null;
//        이메일 인증 o -> true, 이메일 인증 x -> false
        return ResponseEntity.ok().body(signUpResult);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> SignIn(@RequestBody SignupRequestDTO signupRequestDTO, HttpServletResponse resp) {
        Cookie[] cookies = memberService.signIn(signupRequestDTO);
        for (Cookie cookie : cookies) {
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            resp.addCookie(cookie);
        }
        return ResponseEntity.ok().body(new CustomResponseMessage("login success"));
    }

    @GetMapping("/info")
    public ResponseEntity<Object> info(@AuthenticationPrincipal Member member) {
        MemberInfoResponseDTO infoDTO = null;
        if (!ObjectUtils.isEmpty(member)) {
            infoDTO = MemberInfoResponseDTO.from(member);
        }
//        filter 에서 쿠키 확인 후 쿠키가 없다면 null 로 응답...
//        -> 클라이언트 로그인 필요하다는 alert 창
        return ResponseEntity.ok().body(infoDTO);
    }

    @PostMapping("/send-email")
    public ResponseEntity<Object> sendEmail(@RequestParam("email") String email) throws MessagingException {
        memberService.duplicatedEmail(email); //메일 중복
        mailService.sendEmail(email); //검증 링크 전송
          /*
           * 1. 검증 링크 클릭 시 회원가입 화면으로 리다이렉트
           * 2. /verify endpoint 에서 이메일 토큰 유효한지 검증 (10분으로 설정)
           * 3. 이메일 토큰이 유효하면 true 로 응답
           */

        /*
          1. 이메일 sender 누르는 순간 랜덤 숫자 6개 보냄
          2. 랜덤 숫자 6개를 클라이언트 페이지에서 가지고 있음
          3. 이메일의 숫자 6개와 클라이언트 페이지의 숫자를 비교
          4. 동일하다면 이메일 검증 완료
         */

//        temp : postman 사용으로 링크 클릭하면 boolean 담긴 dto 반환하도록 구현
        return ResponseEntity.ok().body(new CustomResponseMessage("success to send email"));
    }

    @GetMapping("/verify")
    public ResponseEntity<Object> verificationEmail(@RequestParam("token") String token, @RequestParam("email") String email) {
        boolean verify = memberService.verificationEmail(token);
        VerificationEmailResponseDTO responseDTO = VerificationEmailResponseDTO.create(email, verify);
        return ResponseEntity.ok().body(responseDTO);
//        이메일 토큰 검증 후 유효한 토큰이면 true 반환
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletResponse resp) {
        Cookie[] deleteCookies = jwtFilter.cookieDelete();
        for (Cookie deleteCookie : deleteCookies) {
            resp.addCookie(deleteCookie);
        }
        return ResponseEntity.ok().body(new CustomResponseMessage("logout success"));
    }
}
