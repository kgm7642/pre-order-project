package com.kgm.preorder.service;

import com.kgm.preorder.entity.EmailAuth;
import com.kgm.preorder.entity.Member;
import com.kgm.preorder.repository.EmailAuthRepository;
import com.kgm.preorder.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
@Slf4j
public class EmailAuthService {

    @Autowired
    private EmailAuthRepository emailAuthRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    private static final Long MAX_EXPIRE_TIME = 5L;

    @Transactional
    public void sendVerificationEmail(Member member) {
        EmailAuth emailAuth = EmailAuth.create(member.getEmail());
        emailAuthRepository.save(emailAuth);

        // 이메일 발송
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(member.getEmail());
            helper.setSubject("회원 가입을 위한 이메일 인증");
            helper.setText("<html><body>계정을 활성화하려면 다음 링크를 클릭하세요: <a href='http://localhost:8080/user/verify?token=" + emailAuth.getAuthToken() + "'>인증 링크</a></body></html>", true);

        } catch (MessagingException e ) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
    }

    @Transactional
    public boolean verifyEmailAuth(String authToken) {
        Optional<EmailAuth> emailAuthOptional = emailAuthRepository
                .findByAuthTokenAndExpired(authToken, false);

        if (emailAuthOptional.isPresent()) {
            EmailAuth emailAuth = emailAuthOptional.get();

            // 사용자 찾기
            Member member = memberRepository.findByEmail(emailAuth.getEmail());

            if (member != null) {
                // 사용자를 활성화 상태로 변경
                member.setEnabled(true);
                memberRepository.save(member);

                // 토큰 사용 및 인증 정보 업데이트
                emailAuth.useToken();
                emailAuthRepository.save(emailAuth);

                return true;
            }
        }

        return false;
    }
}

