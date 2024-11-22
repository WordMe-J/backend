package kr.wordme.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.wordme.model.dto.request.SignupRequestDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity
@Builder
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member implements UserDetails {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false)
    @Getter
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "is_deleted", columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean isDeleted;

    @Column(name = "is_daily_quiz_subscribed", columnDefinition = "TINYINT(1)")
    private Boolean isDailyQuizSubscribed;

    @Column(name = "daily_quiz_subscribed_at")
    private Timestamp dailyQuizSubscribedAt;

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

    @Override
    public boolean isEnabled() {
        return !isDeleted;
    }

    public static Member create(SignupRequestDTO dto, String encodedPassword) {
        return Member.builder()
                .id(UUID.randomUUID())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(encodedPassword)
                .isDeleted(false)
                .build();
    }

    public static Member of(Member member, boolean is_deleted) {
        return Member.builder()
                .id(member.id)
                .email(member.email)
                .password(member.password)
                .nickname(member.nickname)
                .isDeleted(is_deleted)
                .build();
    }
}
