package kr.wordme.service;


import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import kr.wordme.exception.ErrorCode;
import kr.wordme.exception.member.MemberException;
import kr.wordme.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Transactional
@RequiredArgsConstructor
@Slf4j
@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final JwtUtil jwtUtil;

    public void sendEmail(String toEmail) throws MessagingException {
        String emailToken = jwtUtil.createEmailToken(toEmail);
        SimpleMailMessage emailForm = createEmailForm(toEmail, emailToken);
        try {
            mailSender.send(emailForm);
        } catch (MailException e) {
            throw new MemberException(
                    ErrorCode.FAIL_SEND_EMAIL.getStatus(),
                    ErrorCode.FAIL_SEND_EMAIL.getMessage()
            );
        }
    }

    private SimpleMailMessage createEmailForm(String toEmail, String emailToken) {
        String body = "<div>"
                + "<h1> 안녕하세요. Artify 입니다</h1>"
                + "<br>"
                + "<p>아래 링크를 클릭하면 이메일 인증이 완료됩니다.<p>"
                + "<a href='http://localhost:8080/members/verify?token=" + emailToken + "&email=" + toEmail+"'>인증 링크</a>"
                + "</div>";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("WordMe 에서 회원가입 요청을 보냈습니다.");
        message.setText(body);
        return message;
    }
}
