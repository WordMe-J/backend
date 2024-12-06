package kr.wordme.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import kr.wordme.controller.MemberController;
import kr.wordme.filter.JwtFilter;
import kr.wordme.filter.JwtPresenceFilter;
import kr.wordme.filter.JwtValidationFilter;
import kr.wordme.model.dto.request.SignupRequestDTO;
import kr.wordme.service.EmailService;
import kr.wordme.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockCookie;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberService memberService;
    @MockBean
    private EmailService emailService;
    @MockBean
    private JwtFilter jwtFilter;

    @Test
    @DisplayName("로그인 테스트")
    @WithMockUser(value = "test@test.com")
    public void testSignInSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SignupRequestDTO signupRequestDTO = new SignupRequestDTO("test@test.com", "1234", "kkk", true);

        Cookie accessToken = new Cookie("access_token", "1234");
        Cookie refreshToken = new Cookie("refresh_token", "1234");
        when(memberService.signIn(any(SignupRequestDTO.class))).thenReturn(new Cookie[]{accessToken, refreshToken});

        mockMvc.perform(post("/members/sign-in").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequestDTO)))
                .andExpect(status().isOk());
    }
}
