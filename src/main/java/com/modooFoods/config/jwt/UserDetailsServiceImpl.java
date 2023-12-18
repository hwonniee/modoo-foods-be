package com.modooFoods.config.jwt;

import com.modooFoods.exception.CustomException;
import com.modooFoods.member.entity.Member;
import com.modooFoods.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) {
        Optional<Member> memberOptional = memberRepository.findById(Long.parseLong(id));
        memberOptional.orElseThrow(() -> new CustomException(HttpStatus.UNAUTHORIZED, "토큰 정보가 올바르지 않습니다."));
        return new UserDetailsImpl(memberOptional.get());
    }
}
