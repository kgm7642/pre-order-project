package com.kgm.preorder.controller;

import com.kgm.preorder.Dto.ResponseDto.NewPassword;
import com.kgm.preorder.config.security.JwtTokenProvider;
import com.kgm.preorder.entity.Member;
import com.kgm.preorder.repository.MemberRepository;
import com.kgm.preorder.service.MemberService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public ResponseEntity<String> registerUser(@RequestParam("email") String email,
                                               @RequestParam("password") String password,
                                               @RequestParam("name") String name,
                                               @RequestParam("comment") String comment,
                                               @RequestParam("image") MultipartFile image) {
        log.info("회원가입 컨트롤러 접근");
        Member member = new Member(email, password, name, comment);

        // 이미지 저장 및 Member 엔티티에 설정
        memberService.registerMemberWithImage(member, image);

        return ResponseEntity.ok("회원가입 성공");
    }

    // 링크 클릭시 이메일 인증 완료
    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String token) {
        log.info("이메일 인증 컨트롤러 접근");
        log.debug("Entering verifyUser method with token: {}", token);
        if (memberService.verifyMember(token)) {
            return ResponseEntity.ok("이메일 인증 성공");
        } else {
            return ResponseEntity.badRequest().body("토큰이 만료되었습니다");
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

    // 로그아웃
   @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        memberService.logout(token);
        return ResponseEntity.ok("로그아웃 성공");
    }

    // 회원 정보 업데이트
    @PatchMapping("/profile")
    public ResponseEntity<String> updateMemberProfileByEmail(
            @RequestParam("email") String email,
            @RequestParam("name") String newName,
            @RequestParam("comment") String newComment,
            @RequestParam("image") MultipartFile newImage) throws NotFoundException, IOException {
        log.info("회원정보 업데이트 컨트롤러 접근");
        memberService.updateMemberProfileByEmail(email, newName, newComment, newImage);
        return ResponseEntity.ok("프로필 업데이트 성공");
    }

    // 비밀번호 업데이트
    @PatchMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody NewPassword newPassword) {
        log.info("비밀번호 업데이트 컨트롤러 접근");
        memberService.updatePassword(newPassword.getEmail(), newPassword.getNewPassword());
        return ResponseEntity.ok("비밀번호 업데이트 성공");
    }

//    // 테스트
//    @GetMapping("/hello")
//    public String hello() {
//        return "hello";
//    }
}


