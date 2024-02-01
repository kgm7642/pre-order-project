package com.kgm.preorder.service;

import com.kgm.preorder.entity.Member;
import com.kgm.preorder.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EmailAuthService emailAuthService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerMember(Member member) {

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(member.getPassword());

        Member newMember = Member.builder()
                .email(member.getEmail())
                .password(encodedPassword)
                .name(member.getName())
                .comment(member.getComment())
                .image(member.getImage())
                .roles(Collections.singletonList("ROLE_USER")) // ROLE_USER를 기본으로 설정
                .build();

        memberRepository.save(newMember);

        emailAuthService.sendVerificationEmail(newMember);
    }

    @Transactional
    public boolean verifyMember(String authToken) {
        return emailAuthService.verifyEmailAuth(authToken);
    }



}

