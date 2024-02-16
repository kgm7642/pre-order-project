package com.kgm.preorder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class NewsfeedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String activityUser;

    private String targetUser;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "commentId")
    private Reply reply;

    @ManyToOne
    @JoinColumn(name = "likeId")
    private Post_love post_love;

    @ManyToOne
    @JoinColumn(name = "followId")
    private Follow follow;

    private LocalDateTime createdAt;
}
