package com.kgm.preorder.service;

import com.kgm.preorder.Dto.NewsfeedDTO;
import com.kgm.preorder.entity.*;
import com.kgm.preorder.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsfeedService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final PostLoveRepository postLoveRepository;

    // 뉴스피드
    @Transactional
    public List<NewsfeedDTO> getNewsfeed(Long memberId) {
        Member member = memberRepository.getById(memberId);
        List<Member> followingMembers = member.getFollowerMembers();
//        followingMembers.add(member); // 자신도 포함

        List<Post> posts = postRepository.findByMemberIn(followingMembers);
        List<Reply> replies = replyRepository.findByMemberIn(followingMembers);
        List<Post_love> post_loves = postLoveRepository.findByMemberIn(followingMembers);

        List<NewsfeedDTO> newsfeed = new ArrayList<>();

        log.info("posts : {} " , posts);
        for (Post post : posts) {
            NewsfeedDTO activity = new NewsfeedDTO();
            activity.setMember(post.getMember().getUsername());
            activity.setActivityType("Post");
            activity.setContent(post.getContent());
            activity.setCreatedAt(post.getDate());
            newsfeed.add(activity);
        }

        log.info("replies : {} " , replies);
        for (Reply reply : replies) {
            NewsfeedDTO activity = new NewsfeedDTO();
            activity.setMember(reply.getMember().getUsername());
            activity.setActivityType("Reply");
            activity.setContent(reply.getMember().getUsername()+"님이 "+reply.getPost().getMember().getUsername()+"님의 글에 댓글을 남겼습니다.");
            activity.setCreatedAt(reply.getDate());
            newsfeed.add(activity);
        }

        log.info("post_loves : {} " , post_loves);
        for (Post_love post_love : post_loves) {
            NewsfeedDTO activity = new NewsfeedDTO();
            activity.setMember(post_love.getMember().getUsername());
            activity.setActivityType("LOVE");
            activity.setContent(post_love.getMember().getUsername()+"님이 "+post_love.getPost().getMember().getUsername()+"님의 글을 좋아합니다.");
            activity.setCreatedAt(post_love.getDate());
            newsfeed.add(activity);
        }

        log.info("followingMembers : {} " , followingMembers);
        for (Member follow : followingMembers) {
            log.info("follow : {} : " , follow);
            for(Member FromMember : follow.getFollowerMembers()) {
                log.info("FromMember : {} " , FromMember);
                NewsfeedDTO activity = new NewsfeedDTO();
                activity.setMember(follow.getUsername());
                activity.setActivityType("FOLLOW");
                activity.setContent(follow.getUsername()+"님이 "+FromMember.getUsername()+"님을 팔로우 합니다.");
                activity.setCreatedAt(FromMember.getDate());
                newsfeed.add(activity);
            }
        }
        return newsfeed;
    }
}
