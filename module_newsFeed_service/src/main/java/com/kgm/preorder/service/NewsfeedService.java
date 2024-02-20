package com.kgm.preorder.service;

import com.kgm.preorder.Dto.NewsfeedDTO;
import com.kgm.preorder.Dto.ResponseDto.MyPostActionDTO;
import com.kgm.preorder.entity.*;
import com.kgm.preorder.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsfeedService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final PostLoveRepository postLoveRepository;

    // 내가 팔로우한 유저들의 뉴스피드
    @Transactional
    public List<NewsfeedDTO> getNewsfeedTo(Long memberId) {
        Member member = memberRepository.getById(memberId);
        return getNewsfeedDTO(member.getFollowingMembers());
    }

    // 나를 팔로우한 유저들의 뉴스피드
    @Transactional
    public List<NewsfeedDTO> getNewsfeedFrom(Long memberId) {
        Member member = memberRepository.getById(memberId);
        return getNewsfeedDTO(member.getFollowingMembers());
    }

    // 나의 포스트 활동
    @Transactional
    public List<MyPostActionDTO> getMyPostAction(Long memberId) {
        List<Post> posts = postRepository.findByMemberId(memberId);
        List<MyPostActionDTO> postAction = new ArrayList<>();
        for(Post post : posts) {
            for(Reply reply : post.getReplys()) {
                MyPostActionDTO activity = new MyPostActionDTO();
                activity.setActivityType("reply");
                activity.setMember(reply.getMember().getUsername());
                activity.setContent(reply.getMember().getUsername()+"님이 포스트에 댓글을 남겼습니다.");
                activity.setCreatedAt(reply.getDate());
                postAction.add(activity);
            }
            for(Post_love love : post.getPost_loves()) {
                MyPostActionDTO activity = new MyPostActionDTO();
                activity.setActivityType("love");
                activity.setMember(love.getMember().getUsername());
                activity.setContent(love.getMember().getUsername()+"님이 포스트를 좋아합니다.");
                activity.setCreatedAt(love.getDate());
                postAction.add(activity);
            }
        }
        return postAction;
    }

    // 뉴스피드를 반환하는 메서드
    public List<NewsfeedDTO> getNewsfeedDTO(List<Member> members) {
        List<NewsfeedDTO> newsfeed = new ArrayList<>();
        List<Post> posts = postRepository.findByMemberIn(members);
        List<Reply> replies = replyRepository.findByMemberIn(members);
        List<Post_love> post_loves = postLoveRepository.findByMemberIn(members);

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
        log.info("followingMembers : {} " , members);
        for (Member follow : members) {
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
