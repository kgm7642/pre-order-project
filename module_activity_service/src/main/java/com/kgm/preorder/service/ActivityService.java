package com.kgm.preorder.service;

import com.kgm.preorder.Dto.RequestDto.PostLoveRequestDto;
import com.kgm.preorder.Dto.RequestDto.PostRequestDto;
import com.kgm.preorder.Dto.RequestDto.ReplyLoveRequestDto;
import com.kgm.preorder.Dto.RequestDto.ReplyRequestDto;
import com.kgm.preorder.entity.*;
import com.kgm.preorder.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {
    private final MemberRepository memberRepository;
    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final PostLoveRepository postLoveRepository;
    private final ReplyLoveRepository replyLoveRepository;
    private final FollowRepository followRepository;

    // 팔로우 신청
    @Transactional
    public void followMember(String email, Long followingId) {
        log.info("followerId : {} , followingId : {} ", memberRepository.findByEmail(email).getId(), followingId);
        Member follower = memberRepository.findById(memberRepository.findByEmail(email).getId())
                .orElseThrow(() -> new NoSuchElementException("아이디가 존재하지 않음"));

        Member following = memberRepository.findById(followingId)
                .orElseThrow(() -> new NoSuchElementException("아이디가 존재하지 않음"));

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        follow.setDate(LocalDateTime.now());
        followRepository.save(follow);
    }

    // 포스트 등록
    @Transactional
    public void createPost(String email, PostRequestDto postRequestDto) {
        Member member = memberRepository.findById(memberRepository.findByEmail(email).getId()).
                orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));
        Post post = new Post();
        post.setMember(member);
        post.setContent(postRequestDto.getContent());
        post.setDate(LocalDateTime.now());

        postRepository.save(post);
    }

    // 포스트 수정
    @Transactional
    public void updatePost(String email, Long postId, PostRequestDto postRequestDto) {
        Member member = memberRepository.findById(memberRepository.findByEmail(email).getId()).
                orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));
        Post existingPost = postRepository.findById(postId).orElse(null);
        if (existingPost != null) {
            existingPost.setContent(postRequestDto.getContent());
        }
    }

    // 댓글 등록
    @Transactional
    public void createReply(String email, ReplyRequestDto replyRequestDto) {
        Post post = postRepository.findById(replyRequestDto.getPostId())
                .orElseThrow(() -> new RuntimeException("게시물이 존재하지 않습니다"));
        Member member = memberRepository.findById(memberRepository.findByEmail(email).getId()).
                orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));

        Reply reply = new Reply();
        reply.setComment((replyRequestDto.getComment()));
        reply.setMember(member);
        reply.setDate(LocalDateTime.now());
        post.addReply(reply);

        replyRepository.save(reply);
    }

    // 포스트 좋아요
    @Transactional
    public void addPostLove(String email, PostLoveRequestDto postLoveRequestDto) {
        Post post = postRepository.findById(postLoveRequestDto.getPostId())
                .orElseThrow(() -> new RuntimeException("게시물이 존재하지 않습니다"));
        Member member = memberRepository.findById(memberRepository.findByEmail(email).getId()).
                orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));

        Post_love postLove = new Post_love();
        postLove.setMember(member);
        postLove.setDate(LocalDateTime.now());
        postLove.setPost(post);

        postLoveRepository.save(postLove);
    }

    // 댓글 좋아요
    @Transactional
    public void addReplyLove(String email, ReplyLoveRequestDto replyLoveRequestDto) {
        Reply reply = replyRepository.findById(replyLoveRequestDto.getReplyId())
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다"));
        Member member = memberRepository.findById(memberRepository.findByEmail(email).getId()).
                orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));

        Reply_love replyLove = new Reply_love();
        replyLove.setMember(member);
        replyLove.setDate(LocalDateTime.now());
        replyLove.setReply(reply);

        replyLoveRepository.save(replyLove);
    }


}
