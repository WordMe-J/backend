package kr.wordme.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import kr.wordme.model.dto.request.SignupRequestDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member implements UserDetails {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    @Getter
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "is_deleted")
    private Boolean is_deleted;

//    public void passwordEncode(PasswordEncoder passwordEncoder) {
//        this.password = passwordEncoder.encode(this.password);
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public static Member newInstance(SignupRequestDTO dto, String encodedPassword) {
        return Member.builder()
                .id(UUID.randomUUID())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(encodedPassword)
                .is_deleted(false)
                .build();
    }
}
