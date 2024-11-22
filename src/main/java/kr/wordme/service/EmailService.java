package kr.wordme.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import kr.wordme.exception.ErrorCode;
import kr.wordme.exception.member.MemberException;
import kr.wordme.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
        MimeMessage emailForm = createEmailForm(toEmail, emailToken);
        try {
            mailSender.send(emailForm);
        } catch (MailException e) {
            throw new MemberException(
                    ErrorCode.FAIL_SEND_EMAIL.getStatus(),
                    ErrorCode.FAIL_SEND_EMAIL.getMessage()
            );
        }
    }

    private MimeMessage createEmailForm(String toEmail, String emailToken) throws MessagingException {
        String body = "<div>"
                + "<h1> 안녕하세요. WordMe 입니다</h1>"
                + "<br>"
                + "<p>아래 링크를 클릭하면 이메일 인증이 완료됩니다.<p>"
                + "<a href='http://localhost:8080/members/verify?token=" + emailToken + "&email=" + toEmail + "'>인증 링크</a>"
                + "</div>";

        String code = "123456";
        String body_code = "<div>"
                + "<h1> 안녕하세요. WordMe 입니다</h1>"
                + "<br>"
                + "<p>회원가입을 위한 이메일 인증 절차입니다.<p>"
                + "<p>아래의 6자리 인증번호를 이메일 인증 번호 입력란에 입력해주시기 바랍니다.<p>"
                + "</div>"
                + "<div style='background: #8e8eff; align-content: center; justify-items: center'>"
                + "<h2 style='letter-spacing: 10px'>"+code+"</h2>"
                + "</div>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject("WordMe 에서 회원가입 요청을 보냈습니다.");
        helper.setText(body_code, true);
        return message;
    }
}
