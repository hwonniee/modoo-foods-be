package com.modooFoods.member.repository;

import com.example.modooFoods.member.entity.QMember;
import com.modooFoods.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberDslRepository implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Member findByName(String name) {
        return queryFactory.selectFrom(QMember.member)
                .where(QMember.member.name.eq(name))
                .fetchOne();
    }

    @Override
    public Member findByNameAndPassword(String name, String password) {
        return queryFactory.selectFrom(QMember.member)
                .where(QMember.member.name.eq(name)
                        .and(QMember.member.password.eq(password))
                )
                .fetchOne();
    }
}
