package com.kgm.preorder.service;

import com.kgm.preorder.entity.BlacklistToken;
import com.kgm.preorder.entity.MemberImage;
import com.kgm.preorder.entity.Member;
import com.kgm.preorder.repository.BlacklistTokenRepository;
import com.kgm.preorder.repository.MemberImageRepository;
import com.kgm.preorder.repository.MemberRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
@Slf4j
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberImageRepository memberImageRepository;

    @Autowired
    private BlacklistTokenRepository blacklistTokenRepository;

    @Autowired
    private EmailAuthService emailAuthService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ImageService imageService;

    @Value("${image.upload.directory}")
    private String imageUploadDirectory;

    // 회원가입
    @Transactional
    public void registerMemberWithImage(Member member, MultipartFile image) {
        try {
            // 이미지 저장 및 이미지를 업로드할 디렉토리가 존재하지 않으면 생성
            File uploadDir = new File(imageUploadDirectory);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            String filePath = imageUploadDirectory + File.separator + fileName;

            // 이미지를 업로드
            image.transferTo(new File(filePath));

            // 이미지 정보 설정
            MemberImage memberImage = new MemberImage();
            memberImage.setImagePath(fileName);
            member.setImage(memberImage);

            log.info("memberImage {}", memberImage);

            // 이미지 엔티티를 먼저 저장
            memberImageRepository.save(memberImage);

            // 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(member.getPassword());

            // 회원 정보 설정
            Member newMember = Member.builder()
                    .email(member.getEmail())
                    .password(encodedPassword)
                    .name(member.getName())
                    .comment(member.getComment())
                    .roles(Collections.singletonList("ROLE_USER"))
                    .date(LocalDateTime.now())
                    .image(memberImage)
                    .build();

            // 이메일 발송 등 다른 작업 수행
            emailAuthService.sendVerificationEmail(newMember);

        } catch (IOException e) {
            // 예외 처리 로직 추가
            e.printStackTrace();
        }
    }

    // 이메일 인증
    @Transactional
    public boolean verifyMember(String authToken) {
        log.info("메일 인증 서비스 접근");
        return emailAuthService.verifyEmailAuth(authToken);
    }

    // 로그아웃
    @Transactional
    public void logout(String token) {
        if (!blacklistTokenRepository.existsByToken(token)) {
            BlacklistToken blacklistToken = new BlacklistToken();
            blacklistToken.setToken(token);
            blacklistTokenRepository.save(blacklistToken);
        }
    }

    // 프로필 업데이트
    @Transactional
    public void updateMemberProfileByEmail(String email, String newName, String newComment, MultipartFile newImage) throws NotFoundException, IOException {
        log.info("newName : {}, newComment : {}", newName, newComment);
        // 해당 이메일의 멤버를 찾음
        Member member = memberRepository.findOptionalByEmail(email)
                .orElseThrow(() -> new NotFoundException("Member not found with email: " + email));

        log.info("updateMemberProfileByEmail 에서 member를 찾음 {}", member);
        // 이미지 업데이트
        if (newImage != null && !newImage.isEmpty()) {
            log.info("이미지 업데이트 if문 접근");
            String fileName = imageService.updateImage(member.getId(), newImage);

            // 이미지 정보 설정
            MemberImage newImageEntity = new MemberImage();
            newImageEntity.setImagePath(fileName);
            member.setImage(newImageEntity);

            log.info("memberImage {}", newImageEntity);

            // 이미지 엔티티를 먼저 저장
            memberImageRepository.save(newImageEntity);
        }
        log.info("이미지 업데이트 완료");

        // 이름과 인사말 업데이트
        member.setName(newName);
        member.setComment(newComment);

        log.info("이름 인사말 업데이트 완료");

        // 멤버 엔티티 저장
        memberRepository.save(member);

        log.info("레포지토리 업데이트 완료");
    }

    // 비밀번호 변경
    public void updatePassword(String email, String newPassword) {
        Member member = memberRepository.findByEmail(email);

        // 새로운 비밀번호를 인코딩하여 저장
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }
}

