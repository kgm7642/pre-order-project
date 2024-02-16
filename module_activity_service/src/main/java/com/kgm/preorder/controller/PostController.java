package com.kgm.preorder.controller;

import com.kgm.preorder.Dto.RequestDto.PostRequestDto;
import com.kgm.preorder.Dto.RequestDto.PostLoveRequestDto;
import com.kgm.preorder.Dto.RequestDto.ReplyLoveRequestDto;
import com.kgm.preorder.Dto.RequestDto.ReplyRequestDto;
import com.kgm.preorder.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;


    // 포스트 등록
    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody PostRequestDto postRequestDto) {
        log.info("postRequestDto.getMemberId() : {}",postRequestDto.getMemberId());
        postService.createPost(postRequestDto);
        return ResponseEntity.ok("포스트 작성 성공");
    }

    // 댓글 등록
    @PostMapping("/reply")
    public ResponseEntity<String> createReply(@RequestBody ReplyRequestDto replyRequestDto) {
        postService.createReply(replyRequestDto);
        return ResponseEntity.ok("댓글 작성 성공");
    }

    // 포스트 좋아요
    @PostMapping("/post/love")
    public ResponseEntity<String> lovePost(@RequestBody PostLoveRequestDto postLoveRequestDto) {
        postService.addPostLove(postLoveRequestDto);
        return ResponseEntity.ok("포스트 좋아요 성공");
    }

    // 댓글 좋아요
    @PostMapping("/reply/love")
    public ResponseEntity<String> lovePost(@RequestBody ReplyLoveRequestDto replyLoveRequestDto) {
        postService.addReplyLove(replyLoveRequestDto);
        return ResponseEntity.ok("댓글 좋아요 성공");
    }


}
