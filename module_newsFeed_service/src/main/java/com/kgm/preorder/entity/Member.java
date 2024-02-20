package com.kgm.preorder.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@Data
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member implements UserDetails{

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

    @OneToOne
    @JoinColumn(name = "image_id")
    private MemberImage image;

    private boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "following")
    private List<Follow> followerList;

    @OneToMany(mappedBy = "follower")
    private List<Follow> followingList;

    private LocalDateTime date;

    // 나를 팔로우한 유저들
    public List<Member> getFollowingMembers() {
        List<Member> followingMembers = new ArrayList<>();
        for (Follow follow : followingList) {
            followingMembers.add(follow.getFollowing());
        }
        return followingMembers;
    }

    // 내가 팔로우한 유저들
    public List<Member> getFollowerMembers() {
        List<Member> followers = new ArrayList<>();
        for (Follow follow : followerList) {
            followers.add(follow.getFollower());
        }
        return followers;
    }

    @Builder
    public Member(String email, String password, String name, String comment, List<Role> roles, MemberImage image) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.comment = comment;
        this.image = image;
        this.enabled = false; // 초기에는 활성화되지 않은 상태
    }

    public Member(String email, String password, String name, String comment) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.comment = comment;
        this.enabled = false; // 초기에는 활성화되지 않은 상태
    }

    public Member(Long Id) {

    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
