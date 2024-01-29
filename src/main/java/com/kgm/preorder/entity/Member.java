package com.kgm.preorder.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String image;

    private boolean enabled;

    @Builder
    public Member(String email, String password, String name, String comment, String image) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.comment = comment;
        this.image = image;
        this.enabled = false; // 초기에는 활성화되지 않은 상태
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
