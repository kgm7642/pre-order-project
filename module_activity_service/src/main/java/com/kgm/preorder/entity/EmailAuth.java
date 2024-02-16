package com.kgm.preorder.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuth {

    private static final Long MAX_EXPIRE_TIME = 5L; // 5분

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String authToken;
    private Boolean expired;
    private LocalDateTime expireDate;

    @Builder
    public EmailAuth(String email, String authToken, Boolean expired) {
        this.email = email;
        this.authToken = authToken;
        this.expired = expired;
        this.expireDate = LocalDateTime.now().plusMinutes(MAX_EXPIRE_TIME);
    }

    public void useToken() {
        this.expired = true;
    }

    public boolean isTokenExpired() {
        return expired || LocalDateTime.now().isAfter(expireDate);
    }

    public static EmailAuth create(String email) {
        return EmailAuth.builder()
                .email(email)
                .authToken(UUID.randomUUID().toString())
                .expired(false)
                .build();
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }
}


