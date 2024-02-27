package com.kgm.preorder.controller;

import com.kgm.preorder.Dto.RequestDto.*;
import com.kgm.preorder.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
@Slf4j
public class ActivityController {
    private final ActivityService activityService;


    // 팔로우 신청
    @PostMapping("/follow")
    public ResponseEntity<String> followMember(@RequestBody FollowRequestDto request) {
        log.info("팔로우 컨트롤러 접근");
        activityService.followMember(request.getFollowerId(), request.getFollowingId());
        return ResponseEntity.badRequest().body("팔로우 성공");
    }

    // 포스트 등록
    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody PostRequestDto postRequestDto) {
        log.info("postRequestDto.getMemberId() : {}",postRequestDto.getMemberId());
        activityService.createPost(postRequestDto);
        return ResponseEntity.ok("포스트 작성 성공");
    }

    // 댓글 등록
    @PostMapping("/reply")
    public ResponseEntity<String> createReply(@RequestBody ReplyRequestDto replyRequestDto) {
        activityService.createReply(replyRequestDto);
        return ResponseEntity.ok("댓글 작성 성공");
    }

    // 포스트 좋아요
    @PostMapping("/post/love")
    public ResponseEntity<String> lovePost(@RequestBody PostLoveRequestDto postLoveRequestDto) {
        activityService.addPostLove(postLoveRequestDto);
        return ResponseEntity.ok("포스트 좋아요 성공");
    }

    // 댓글 좋아요
    @PostMapping("/reply/love")
    public ResponseEntity<String> lovePost(@RequestBody ReplyLoveRequestDto replyLoveRequestDto) {
        activityService.addReplyLove(replyLoveRequestDto);
        return ResponseEntity.ok("댓글 좋아요 성공");
    }
}
