package com.kgm.preorder.controller;

import com.kgm.preorder.Dto.DefaultRes;
import com.kgm.preorder.Dto.RequestDto.LoginRequestDTO;
import com.kgm.preorder.Dto.RequestDto.UpdatePWRequestDTO;
import com.kgm.preorder.Dto.ResponseDto.UpdateResponseDTO;
import com.kgm.preorder.Dto.ResponseMessage;
import com.kgm.preorder.Dto.StatusCode;
import com.kgm.preorder.config.jwt.JwtUtil;
import com.kgm.preorder.config.security.JwtTokenProvider;
import com.kgm.preorder.entity.Member;
import com.kgm.preorder.repository.MemberRepository;
import com.kgm.preorder.service.MemberService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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
    public ResponseEntity<String> verifyUser(@RequestParam String token, @RequestParam String expireDate) {
        String dateString = expireDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
//        Date date = java.sql.Timestamp.valueOf(dateTime);

        log.info("LocalDateTime.now().isAfter(expireDate) {} " , LocalDateTime.now().isAfter(dateTime));
        log.info("이메일 인증 컨트롤러 접근");
        if (memberService.verifyMember(token) && !LocalDateTime.now().isAfter(dateTime)) {
            return ResponseEntity.ok("이메일 인증 성공");
        } else {
            return ResponseEntity.badRequest().body("토큰이 만료되었습니다");
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO loginRequestDTO) {
        log.info("로그인 컨트롤러 접근");
        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();

        // 이메일과 비밀번호로 사용자 찾기
        Member member = memberRepository.findByEmail(email);

        // 멤버가 Null 이거나 현재 입력받은 비밀번호와 DB의 비밀번호가 다를 경우
        if (member == null || !passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("가입되지 않은 E-MAIL이거나 비밀번호가 일치하지 않습니다.");
        }


        // 로그인 성공 시 토큰 생성
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS,
                jwtTokenProvider.createToken(member.getUsername(), member.getRoles())), HttpStatus.OK);
    }

    // 로그아웃
   @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
       log.info("로그아웃 컨트롤러 접근");
        memberService.logout(token);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.LOGOUT_SUCCESS), HttpStatus.OK);
    }

    // 회원 정보 업데이트
    @PatchMapping("/profile")
    public ResponseEntity updateMemberProfileByEmail(
            @RequestHeader("Authorization") String token,
            @RequestParam("newName") String newName,
            @RequestParam("newComment") String newComment,
            @RequestParam("newImage") MultipartFile newImage) throws NotFoundException, IOException {
        log.info("회원정보 업데이트 컨트롤러 접근");
        String email = jwtUtil.getEmailFromToken(token);
        memberService.updateMemberProfileByEmail(email, newName, newComment, newImage);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_USER, new UpdateResponseDTO(newName,newComment)), HttpStatus.OK);
    }

    // 비밀번호 업데이트
    @PatchMapping("/password")
    public ResponseEntity updatePassword(@RequestHeader("Authorization") String token, @RequestBody UpdatePWRequestDTO updatePWRequestDTO) {
        log.info("비밀번호 수정 컨트롤러 접근");
        String email = jwtUtil.getEmailFromToken(token);
        log.info("email {} ", email);
        memberService.updatePassword(email, updatePWRequestDTO.getNewPW());
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_USER_PW), HttpStatus.OK);
    }

//    // 테스트
//    @GetMapping("/hello")
//    public String hello() {
//        return "hello";
//    }
}


