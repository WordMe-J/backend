package kr.wordme.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.wordme.model.entity.QMember;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public boolean existsByNickname(String nickname) {
        QMember qMember = QMember.member;
        Integer result =  queryFactory
                            .selectOne()
                            .from(qMember)
                            .where(qMember.nickname.eq(nickname))
                            .fetchFirst();
        return result != null;
    }
}
