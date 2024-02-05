package com.kgm.preorder.controller;

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

    @GetMapping("/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getNewsfeed(@PathVariable String MemberId) {
        List<Map<String, Object>> newsfeed = newsfeedService.getNewsfeed(MemberId);
        return ResponseEntity.ok(newsfeed);
    }

}
