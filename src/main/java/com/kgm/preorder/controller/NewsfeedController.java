package com.kgm.preorder.controller;

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
import java.util.Map;

@RestController
@RequestMapping("/newsfeed")
@RequiredArgsConstructor
@Slf4j
public class NewsfeedController {

    private final NewsfeedService newsfeedService;

    @GetMapping("/{MemberId}")
    public ResponseEntity<List<NewsfeedDTO>> getNewsfeed(@PathVariable String MemberId) {
        log.info("뉴스피드 컨트롤러 접근");
        List<NewsfeedDTO> newsfeed = newsfeedService.getNewsfeed(Long.valueOf(MemberId));
        return ResponseEntity.ok(newsfeed);
    }

}
