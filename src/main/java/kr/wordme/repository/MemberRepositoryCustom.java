package kr.wordme.repository;

public interface MemberRepositoryCustom {
    boolean existsByNickname(String nickname);
}
