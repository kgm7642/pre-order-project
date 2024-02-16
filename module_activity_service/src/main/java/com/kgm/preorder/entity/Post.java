package com.kgm.preorder.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(length = 1000, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Reply> replys = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Post_love> post_loves = new ArrayList<>();

    public void addReply(Reply reply) {
        replys.add(reply);
        reply.setPost(this);
    }

    public void removeReply(Reply reply) {
        replys.remove(reply);
        reply.setPost(null);
    }

    private LocalDateTime date;
}