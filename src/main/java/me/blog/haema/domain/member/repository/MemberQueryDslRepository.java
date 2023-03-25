package me.blog.haema.domain.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.blog.haema.domain.member.dto.MemberFindResponseDto;
import me.blog.haema.domain.member.dto.MemberRequestDto;
import me.blog.haema.domain.member.entity.persist.Member;
import me.blog.haema.domain.member.entity.persist.QMember;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberQueryDslRepository {

    private final JPAQueryFactory query;

    public List<MemberFindResponseDto> findAllMembers(Pageable pageable) {
        QMember m = QMember.member;
        return query.select(Projections.constructor(MemberFindResponseDto.class, m.email, m.nickname))
                .from(m).limit(pageable.getPageSize()).fetch();
    }

    public Member findMember(MemberRequestDto requestDto){
        QMember m = QMember.member;
        return query.select(m).from(m).where(eqEmailType(requestDto.getEmail(), m)).fetchOne();
    }

    private BooleanExpression eqEmailType(String email, QMember member) {
        if (email == null) {
            return null;
        }
        return member.email.eq(email);
    }

    private BooleanExpression eqNameType(String nickname, QMember member) {
        if (nickname == null) {
            return null;
        }
        return member.nickname.eq(nickname);
    }

    private BooleanExpression eqPasswordType(String password, QMember member) {
        if (password == null) {
            return null;
        }
        return member.password.eq(password);
    }
}