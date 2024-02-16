package com.kgm.preorder.service;

import com.kgm.preorder.Dto.RequestDto.PostRequestDto;
import com.kgm.preorder.Dto.RequestDto.PostLoveRequestDto;
import com.kgm.preorder.Dto.RequestDto.ReplyLoveRequestDto;
import com.kgm.preorder.Dto.RequestDto.ReplyRequestDto;
import com.kgm.preorder.entity.*;
import com.kgm.preorder.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {


    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final PostLoveRepository postLoveRepository;
    private final ReplyLoveRepository replyLoveRepository;

    // 포스트 등록
    public void createPost(PostRequestDto postRequestDto) {
        Member member = memberRepository.findById(postRequestDto.getMemberId()).
                orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));
        Post post = new Post();
        post.setMember(member);
        post.setContent(postRequestDto.getContent());
        post.setDate(LocalDateTime.now());

        postRepository.save(post);
    }

    // 댓글 등록
    public void createReply(ReplyRequestDto replyRequestDto) {
        Post post = postRepository.findById(replyRequestDto.getPostId())
                .orElseThrow(() -> new RuntimeException("게시물이 존재하지 않습니다"));
        Member member = memberRepository.findById(replyRequestDto.getMemberId()).
                orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));

        Reply reply = new Reply();
        reply.setComment((replyRequestDto.getComment()));
        reply.setMember(member);
        reply.setDate(LocalDateTime.now());
        post.addReply(reply);

        replyRepository.save(reply);
    }

    // 포스트 좋아요
    public void addPostLove(PostLoveRequestDto postLoveRequestDto) {
        Post post = postRepository.findById(postLoveRequestDto.getPostId())
                .orElseThrow(() -> new RuntimeException("게시물이 존재하지 않습니다"));
        Member member = memberRepository.findById(postLoveRequestDto.getMemberId()).
                orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));

        Post_love postLove = new Post_love();
        postLove.setMember(member);
        postLove.setDate(LocalDateTime.now());
        postLove.setPost(post);

        postLoveRepository.save(postLove);
    }

    // 댓글 좋아요
    public void addReplyLove(ReplyLoveRequestDto replyLoveRequestDto) {
        Reply reply = replyRepository.findById(replyLoveRequestDto.getReplyId())
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다"));
        Member member = memberRepository.findById(replyLoveRequestDto.getMemberId()).
                orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));

        Reply_love replyLove = new Reply_love();
        replyLove.setMember(member);
        replyLove.setDate(LocalDateTime.now());
        replyLove.setReply(reply);

        replyLoveRepository.save(replyLove);
    }
}
