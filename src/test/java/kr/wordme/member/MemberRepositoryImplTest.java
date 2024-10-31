package kr.wordme.member;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.wordme.repository.MemberRepositoryImpl;

@SpringBootTest
public class MemberRepositoryImplTest {
    @Autowired
    private MemberRepositoryImpl memberRepositoryImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testQuerydslMethod() {
        boolean test = memberRepositoryImpl.existsByNickname("UserFive");
        System.out.println(test);
    }
}
