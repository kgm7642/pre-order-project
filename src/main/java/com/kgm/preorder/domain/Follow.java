package com.kgm.preorder.domain;

public class Follow {

    private Long id;
    private User follower;

    private User following;

    public Follow(User follower, User following){
        this.follower = follower;
        this.following = following;
    }
}
