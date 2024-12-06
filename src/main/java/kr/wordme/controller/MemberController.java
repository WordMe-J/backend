package kr.wordme.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.wordme.common.ApiResponse;
import kr.wordme.exception.member.DuplicateException;
import kr.wordme.exception.member.InvalidParamException;
import kr.wordme.exception.member.MemberException;
import kr.wordme.filter.JwtFilter;
import kr.wordme.model.dto.request.SignupRequestDTO;
import kr.wordme.model.dto.response.MemberInfoResponseDTO;
import kr.wordme.model.dto.response.VerificationEmailResponseDTO;
import kr.wordme.model.entity.Member;
import kr.wordme.service.EmailService;
import kr.wordme.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/members")
@Slf4j
public class MemberController {
    /**
     * # 자사 회원가입 & 로그인 1. 이메일 검증 (30분 유효한 임시 토큰으로 검증) 2. 이메일 검증 결과(isVerify), 비밀번호 등등 담긴
     * signupRequestDTO 로 회원가입 진행 3. 검증 결과가 true 일 때만 회원가입 완료
     * <p/>
     * 4. 이메일, 비밀번호로 로그인 진행 access, refresh token cookie 에 담고 index redirect # oAuth2 회원가입 & 로그인 1.
     * DB에 같은 이메일 있는지 확인 후 DB에 저장 2. 회원가입 / 로그인 성공 시 token cookie 에 담고 index redirect 3. Header 에
     * token 담고 정보 요청
     **/

    private final MemberService memberService;
    private final EmailService mailService;
    private final JwtFilter jwtFilter;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<Boolean>> signUp(@RequestBody SignupRequestDTO signupRequestDTO) {
        Member member = memberService.signUp(signupRequestDTO);
        boolean signUpResult = member != null;
        return ResponseEntity.ok().body(ApiResponse.ok(signUpResult));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<Boolean>> SignIn(@RequestBody SignupRequestDTO signupRequestDTO,
                                         HttpServletResponse resp) {
        Cookie[] cookies = memberService.signIn(signupRequestDTO);
        for (Cookie cookie : cookies) {
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            resp.addCookie(cookie);
        }
        boolean signInCookie = !ObjectUtils.isEmpty(cookies);
        return ResponseEntity.ok().body(ApiResponse.ok(signInCookie));
    }

    @GetMapping("/info")
    public ResponseEntity<ApiResponse<MemberInfoResponseDTO>> info(@AuthenticationPrincipal Member member) {
        return Optional.ofNullable(member)
                .map(MemberInfoResponseDTO::from)
                .map(infoDTO -> ResponseEntity.ok().body(ApiResponse.ok(infoDTO)))
                .orElseGet(() ->ResponseEntity.ok().body(ApiResponse.of(HttpStatus.NOT_FOUND, "Please Log in", null)));
    }

    @PostMapping("/send-email")
    public ResponseEntity<ApiResponse<Boolean>> sendEmail(@RequestParam("email") String email) {
        try {
            return ResponseEntity.ok().body(ApiResponse.ok(mailService.sendEmail(email)));
        } catch (MemberException e) {
            return ResponseEntity.ok().body(ApiResponse.of(HttpStatus.BAD_REQUEST, "fail to send Email", false));
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<VerificationEmailResponseDTO>> verificationEmail(@RequestParam("token") String token,
                                                    @RequestParam("email") String email) {
        boolean verify = memberService.verificationEmail(token);
        VerificationEmailResponseDTO responseDTO =
                VerificationEmailResponseDTO.create(email, verify);
        return ResponseEntity.ok().body(ApiResponse.ok(responseDTO));
        // 이메일 토큰 검증 후 유효한 토큰이면 true 반환
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<Boolean>> logout(HttpServletResponse resp) {
        Cookie[] deleteCookies = jwtFilter.cookieDelete();
        boolean cookieDeleted = true;

        for (Cookie deleteCookie : deleteCookies) {
            try {
                resp.addCookie(deleteCookie);
            } catch (Exception e) {
                cookieDeleted = false;
                break;
            }
        }
        return ResponseEntity.ok().body(ApiResponse.ok(cookieDeleted));
    }

    @GetMapping("/exists/email")
    public ResponseEntity<ApiResponse<Boolean>> existsByEmail(
            @RequestParam(name = "email") String email) {
        Optional.ofNullable(email).filter(e -> !e.isBlank())
                .orElseThrow(() -> new InvalidParamException(HttpStatus.BAD_REQUEST, "email"));

        return Optional.of(memberService.existsByEmail(email)).filter(result -> !result)
                .map(result -> ResponseEntity.ok().body(ApiResponse.ok(result)))
                .orElseThrow(() -> new DuplicateException(HttpStatus.CONFLICT, "email"));
    }

    @GetMapping("/exists/nickname")
    public ResponseEntity<ApiResponse<Boolean>> existsByNickname(
            @RequestParam(name = "nickname") String nickname) {
        Optional.ofNullable(nickname).filter(e -> !e.isBlank())
                .orElseThrow(() -> new InvalidParamException(HttpStatus.BAD_REQUEST, "nickname"));

        return Optional.of(memberService.existsByNickname(nickname)).filter(result -> !result)
                .map(result -> ResponseEntity.ok().body(ApiResponse.ok(result)))
                .orElseThrow(() -> new DuplicateException(HttpStatus.CONFLICT, "nickname"));
    }

    @PostMapping("/delete-member")
    public ResponseEntity<ApiResponse<Member>> deleteMember(@AuthenticationPrincipal Member member) {
        Member deleteMember = memberService.deleteMember(member);
        return ResponseEntity.ok().body(ApiResponse.ok(deleteMember));
    }
}
