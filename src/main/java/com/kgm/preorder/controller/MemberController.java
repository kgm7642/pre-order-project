package com.kgm.preorder.controller;

import com.kgm.preorder.config.security.JwtTokenProvider;
import com.kgm.preorder.entity.Member;
import com.kgm.preorder.repository.MemberRepository;
import com.kgm.preorder.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Member member) {

        memberService.registerMember(member);

        return ResponseEntity.ok("Registration successful. Please check your email for verification.");
    }

    // 링크 클릭시 이메일 인증 완료
    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String token) {
        if (memberService.verifyMember(token)) {
            return ResponseEntity.ok("Email verification successful. Your account is now activated.");
        } else {
            return ResponseEntity.badRequest().body("Invalid verification token or the token has expired.");
        }
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        Member member = memberRepository.findByEmail(user.get("email"));

        if (member == null) {
            throw new IllegalArgumentException("가입되지 않은 E-MAIL 입니다.");
        }

        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}


