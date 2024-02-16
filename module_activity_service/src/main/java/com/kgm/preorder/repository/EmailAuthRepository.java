package com.kgm.preorder.repository;

import com.kgm.preorder.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {
    Optional<EmailAuth> findByAuthToken(String authToken);
    void deleteByEmail(String email);
    Optional<EmailAuth> findByEmailAndAuthTokenAndExpired(String email, String authToken, boolean expired);
    Optional<EmailAuth> findByAuthTokenAndExpired(String authToken, boolean expired); // 추가
}
