package com.kgm.preorder.service;

import com.kgm.preorder.Dto.NewsfeedItem;
import com.kgm.preorder.repository.NewsfeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsfeedService {

    private final NewsfeedRepository newsfeedRepository;

    public List<Map<String, Object>> getNewsfeed(String memberId) {
        List<Map<String, Object>> newsfeed = new ArrayList<>();

        List<NewsfeedItem> newsfeedItems = newsfeedRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
        for (NewsfeedItem newsfeedItem : newsfeedItems) {
            Map<String, Object> result = new HashMap<>();
            result.put("user", newsfeedItem.getMember());
            result.put("type", newsfeedItem.getType());
            result.put("content", newsfeedItem.getContent());
            result.put("createdAt", newsfeedItem.getCreatedAt());
            // 추가 필요한 정보들을 넣어줄 수 있음
            newsfeed.add(result);
        }

        return newsfeed;
    }
}
