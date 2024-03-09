package com.kgm.preorder.controller;

import com.kgm.preorder.Dto.DefaultRes;
import com.kgm.preorder.Dto.RequestDto.*;
import com.kgm.preorder.Dto.ResponseMessage;
import com.kgm.preorder.Dto.StatusCode;
import com.kgm.preorder.config.jwt.JwtUtil;
import com.kgm.preorder.repository.BlacklistTokenRepository;
import com.kgm.preorder.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
@Slf4j
public class ActivityController {
    private final ActivityService activityService;
    private final BlacklistTokenRepository blacklistTokenRepository;
    private final JwtUtil jwtUtil;

    // 팔로우 신청
    @PostMapping("/follow")
    public ResponseEntity<String> followMember(@RequestHeader("Authorization") String token, @RequestBody FollowRequestDto request) {
        log.info("팔로우 컨트롤러 접근");
        if(blacklistTokenRepository.existsByToken(token)) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.TOEKN_EXPIRE), HttpStatus.OK);
        }
        String email = jwtUtil.getEmailFromToken(token);
        activityService.followMember(email, request.getFollowingId());
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.FOLLOW_SUCCESS), HttpStatus.OK);
    }

    // 포스트 등록
    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestHeader("Authorization") String token, @RequestBody PostRequestDto postRequestDto) {
        log.info("포스트 등록 컨트롤러 접근");
        if(blacklistTokenRepository.existsByToken(token)) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.TOEKN_EXPIRE), HttpStatus.OK);
        }
        String email = jwtUtil.getEmailFromToken(token);
        activityService.createPost(email, postRequestDto);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.POST_SUCCESS), HttpStatus.OK);
    }

    // 포스트 수정
    @PatchMapping("/post/{postId}")
    public ResponseEntity updatePost(@RequestHeader("Authorization") String token, @PathVariable Long postId,  @RequestBody PostRequestDto postRequestDto) {
        log.info("포스트 수정 컨트롤러 접근");
        if(blacklistTokenRepository.existsByToken(token)) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.TOEKN_EXPIRE), HttpStatus.OK);
        }
        String email = jwtUtil.getEmailFromToken(token);
        activityService.updatePost(email, postId, postRequestDto);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.POST_UPDATE_SUCCESS), HttpStatus.OK);
    }

    // 댓글 등록
    @PostMapping("/reply")
    public ResponseEntity<String> createReply(@RequestHeader("Authorization") String token, @RequestBody ReplyRequestDto replyRequestDto) {
        if(blacklistTokenRepository.existsByToken(token)) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.TOEKN_EXPIRE), HttpStatus.OK);
        }
        String email = jwtUtil.getEmailFromToken(token);
        activityService.createReply(email, replyRequestDto);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.REPLY_SUCCESS), HttpStatus.OK);
    }

    // 포스트 좋아요
    @PostMapping("/post/love")
    public ResponseEntity<String> lovePost(@RequestHeader("Authorization") String token, @RequestBody PostLoveRequestDto postLoveRequestDto) {
        if(blacklistTokenRepository.existsByToken(token)) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.TOEKN_EXPIRE), HttpStatus.OK);
        }
        String email = jwtUtil.getEmailFromToken(token);
        activityService.addPostLove(email, postLoveRequestDto);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.POST_LOVE_SUCCESS), HttpStatus.OK);
    }

    // 댓글 좋아요
    @PostMapping("/reply/love")
    public ResponseEntity<String> lovePost(@RequestHeader("Authorization") String token, @RequestBody ReplyLoveRequestDto replyLoveRequestDto) {
        if(blacklistTokenRepository.existsByToken(token)) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.TOEKN_EXPIRE), HttpStatus.OK);
        }
        String email = jwtUtil.getEmailFromToken(token);
        activityService.addReplyLove(email, replyLoveRequestDto);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.REPLY_LOVE_SUCCESS), HttpStatus.OK);
    }
}
