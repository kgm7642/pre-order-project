package com.kgm.preorder.controller;

import com.kgm.preorder.entity.Member;
import com.kgm.preorder.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class MemberController {

    @Autowired
    private MemberService memberService;

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
}


