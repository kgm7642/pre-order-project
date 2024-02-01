package com.kgm.preorder.controller;

import com.kgm.preorder.Dto.NewPassword;
import com.kgm.preorder.config.security.JwtTokenProvider;
import com.kgm.preorder.entity.Member;
import com.kgm.preorder.repository.MemberRepository;
import com.kgm.preorder.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

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
    public String login(@RequestBody Member loginRequest) {
        log.info("로그인 컨트롤러 접근");
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // 이메일과 비밀번호로 사용자 찾기
        Member member = memberRepository.findByEmail(email);

        // 멤버가 Null 이거나 현재 입력받은 비밀번호와 DB의 비밀번호가 다를 경우
        if (member == null || !passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("가입되지 않은 E-MAIL이거나 비밀번호가 일치하지 않습니다.");
        }

        // 로그인 성공 시 토큰 생성
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }


    @PatchMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody NewPassword newPassword) {
        log.info("비밀번호 업데이트 컨트롤러 접근");
        memberService.updatePassword(newPassword.getEmail(), newPassword.getNewPassword());
        return ResponseEntity.ok("Password updated successfully.");
    }


    // 테스트
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}


