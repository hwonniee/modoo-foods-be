package com.modooFoods.member.repository;

import com.modooFoods.member.entity.Member;

public interface MemberRepositoryCustom {
    Member findByName(String name);

    Member findByNameAndPassword(String name, String password);
}
