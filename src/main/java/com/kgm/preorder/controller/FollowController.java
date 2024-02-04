package com.kgm.preorder.controller;

import com.kgm.preorder.Dto.RequestDto.FollowRequestDto;
import com.kgm.preorder.service.FollowService;
import com.kgm.preorder.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
@Slf4j
public class FollowController {

    private final FollowService followService;

    private final MemberService memberService;

    @PostMapping("/follow")
    public ResponseEntity<String> followMember(@RequestBody FollowRequestDto request) {
        log.info("팔로우 컨트롤러 접근");
        followService.followMember(request.getFollowerId(), request.getFollowingId());

        return ResponseEntity.badRequest().body("팔로우 성공");
    }

}
