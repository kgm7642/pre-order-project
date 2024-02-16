package com.kgm.preorder.service;

import com.kgm.preorder.entity.Follow;
import com.kgm.preorder.entity.Member;
import com.kgm.preorder.repository.FollowRepository;
import com.kgm.preorder.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    // 팔로우 신청
    @Transactional
    public void followMember(Long followerId, Long followingId) {
        log.info("followerId : {} , followingId : {} ", followerId, followingId);
        Member follower = memberRepository.findById(followerId)
                .orElseThrow(() -> new NoSuchElementException("아이디가 존재하지 않음"));

        Member following = memberRepository.findById(followingId)
                .orElseThrow(() -> new NoSuchElementException("아이디가 존재하지 않음"));

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        follow.setDate(LocalDateTime.now());
        followRepository.save(follow);
    }
}
