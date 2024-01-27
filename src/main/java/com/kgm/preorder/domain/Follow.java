package com.kgm.preorder.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "follow")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Users follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private Users following;

    public Follow(Users follower, Users following){
        this.follower = follower;
        this.following = following;
    }
}