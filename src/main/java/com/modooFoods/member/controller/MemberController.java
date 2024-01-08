package com.modooFoods.member.controller;


import com.modooFoods.config.ApiDocumentResponse;
import com.modooFoods.member.service.MemberService;
import com.modooFoods.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@ApiDocumentResponse
@RestController
@RequiredArgsConstructor
@RequestMapping("members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 목록", description = "가입한 모든 회원정보를 불러옵니다.")
    @GetMapping
    List<Member> findMembers() {
        return memberService.findMembers();
    }

    @GetMapping("dtl")
    Member findMemberById(@RequestParam long id) {
        return memberService.findOne(id);
    }

    @PostMapping("sign-up")
    public Long signUpMember(@RequestBody Member member) {
        return memberService.signUpMember(member);
    }

    @PostMapping("sign-in")
    public String signInMember(@RequestBody Member member) {
        return memberService.signInMember(member);
    }

    @PutMapping
    public Long updateMember(@RequestBody Member member) {
        return memberService.signUpMember(member);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteMember(@RequestParam long id) {
        memberService.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
