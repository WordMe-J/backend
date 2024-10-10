package kr.wordme.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class KafkaEmailListener {

    private JavaMailSender mailSender;
    private KafkaListenerEndpointRegistry registry;

    @KafkaListener(topics = "vocabulary-category-*", groupId = "email-group", autoStartup = "false")
    public void listen(ConsumerRecord<String, String> record) {
        sendEmail(record.value());
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void startListeners() {
        registry.start();
    }

    @Scheduled(cron = "0 30 9 * * ?")
    public void stopListeners() {
        registry.stop();
    }

    private void sendEmail(String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("user@example.com"); // 실제 사용자 이메일로 설정
        mailMessage.setSubject("Your English Vocabulary Cards");
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}
