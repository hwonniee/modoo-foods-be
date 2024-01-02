package com.modooFoods.member.service;

import com.modooFoods.member.repository.MemberRepository;
import com.modooFoods.config.jwt.JwtUtils;
import com.modooFoods.exception.CustomException;
import com.modooFoods.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;

    @Transactional
    public Long signUpMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member).getId();
    }

    public String signInMember(Member member) {
        Member findMember = memberRepository.findByNameAndPassword(member.getName(), member.getPassword());
        if (findMember == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이름 또는 비밀번호가 틀립니다.");
        }

        return jwtUtils.createAccessToken(findMember);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByName(member.getName());
        if (findMember != null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");
        }
        return memberRepository.findById(memberId).get();
    }

    public void deleteMember(long id) {
        Member member = findOne(id);
        memberRepository.deleteById(id);
    }
}
