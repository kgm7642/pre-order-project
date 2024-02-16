package com.kgm.preorder.controller;

import com.kgm.preorder.Dto.MyPostActionDTO;
import com.kgm.preorder.Dto.NewsfeedDTO;
import com.kgm.preorder.service.NewsfeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/newsfeed")
@RequiredArgsConstructor
@Slf4j
public class NewsfeedController {

    private final NewsfeedService newsfeedService;

    // 내가 팔로우한 유저들의 뉴스피드
    @GetMapping("/getNewsfeedTo/{MemberId}")
    public ResponseEntity<List<NewsfeedDTO>> getNewsfeedTo(@PathVariable String MemberId) {
        log.info("뉴스피드 컨트롤러 접근");
        List<NewsfeedDTO> newsfeed = newsfeedService.getNewsfeedTo(Long.valueOf(MemberId));
        return ResponseEntity.ok(newsfeed);
    }

    // 나를 팔로우한 유저들의 뉴스피드
    @GetMapping("/getNewsfeedFrom/{MemberId}")
    public ResponseEntity<List<NewsfeedDTO>> getNewsfeedFrom(@PathVariable String MemberId) {
        log.info("뉴스피드 컨트롤러 접근");
        List<NewsfeedDTO> newsfeed = newsfeedService.getNewsfeedFrom(Long.valueOf(MemberId));
        return ResponseEntity.ok(newsfeed);
    }

    // 나의 포스트 활동
    @GetMapping("/MyPostAction/{MemberId}")
    public ResponseEntity<List<MyPostActionDTO>> getMyPostAction(@PathVariable String MemberId) {
        log.info("포스트액션 컨트롤러 접근");
        List<MyPostActionDTO> myPostActions = newsfeedService.getMyPostAction(Long.valueOf(MemberId));
        return ResponseEntity.ok(myPostActions);
    }
}
